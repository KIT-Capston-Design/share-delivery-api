package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.payment.PaymentRepository;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.dto.paymentdiscount.PaymentDiscountEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.*;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;

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

        Payment payment = paymentRepository.save(dto.toEntity(room));

        List<PaymentDiscountEnrollRequestDTO> discounts = dto.getDiscounts();

        paymentDiscountService.enrollPaymentDiscount(discounts, payment);

        paymentOrderFormService.enrollPaymentOrderForm(images, payment);


        // 총 할인 금액
        long totalDiscountAmount = discounts.stream().map(PaymentDiscountEnrollRequestDTO::getAmount).mapToLong(Long::longValue).sum();

        //Remittance Entity 생성
        remittanceService.createRemittanceEntities(room, payment, totalDiscountAmount);


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
}
