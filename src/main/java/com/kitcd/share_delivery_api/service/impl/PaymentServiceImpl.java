package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.payment.PaymentRepository;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderForm;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.dto.paymentdiscount.PaymentDiscountEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.*;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    private final PaymentDiscountService paymentDiscountService;

    private final PaymentOrderFormService paymentOrderFormService;

    private final ActivatedDeliveryRoomInfoRedisRepository activatedDeliveryRoomInfoRedisRepository;

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final RemittanceService remittanceService;

    @Override
    public DeliveryRoomState enrollPayment(PaymentEnrollRequestDTO dto, List<MultipartFile> images, DeliveryRoom room) {

        //방장인지 체크
        room.checkLeader(ContextHolder.getAccountId());

        room.enrollPaymentStatusCheck();

        if(images == null || images.isEmpty()){
            throw new IllegalArgumentException("주문서 이미지가 존재해야 합니다.");
        }


        Payment payment = paymentRepository.save(dto.toEntity(room));

        //주문서 등록
        paymentOrderFormService.enrollPaymentOrderForm(images, payment);

        long totalDiscounts = 0;

        //할인 객체 등록
        List<PaymentDiscountEnrollRequestDTO> discounts = dto.getDiscounts();

        if(discounts != null && !discounts.isEmpty()){
            List<PaymentDiscount> paymentDiscounts = paymentDiscountService.enrollPaymentDiscount(discounts, payment);
            totalDiscounts = paymentDiscounts.stream().map(PaymentDiscount::getAmount).mapToLong(Long::longValue).sum();
        }


        //Remittance Entity 생성
        remittanceService.createRemittanceEntities(room, payment, totalDiscounts);

        //그룹키 가져오고

        ActivatedDeliveryRoomInfo activatedDeliveryRoomInfo = activatedDeliveryRoomInfoRedisRepository.findByDeliveryRoomId(room.getDeliveryRoomId());

        //전송할 메시지 생성
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.COMPLETED_ORDER);
        data.put("roomId", room.getDeliveryRoomId());

        //전송
        firebaseCloudMessageService.sendMessageTo(
                activatedDeliveryRoomInfo.getFcmGroupToken(),
                room.getContent() + "배달 주문 정보가 등록되었습니다.",
                "null",
                data);

        return room.getStatus();
    }

    @Override
    public Payment getByDeliveryRoomId(Long deliveryRoomId) {
        Payment payment = paymentRepository.getByDeliveryRoomId(deliveryRoomId);

        if(payment == null) throw new FetchNotFoundException(Payment.class.toString(), deliveryRoomId + "(DeliveryRoom)");

        return payment;
    }
}
