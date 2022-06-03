package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.payment.PaymentRepository;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderForm;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void enrollPayment(PaymentEnrollRequestDTO dto, List<MultipartFile> images, DeliveryRoom room) {


        Payment payment = paymentRepository.save(dto.toEntity(room));

        paymentDiscountService.enrollPaymentDiscount(dto.getDiscounts(), payment);

        paymentOrderFormService.enrollPaymentOrderForm(images,payment);

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
    }
}
