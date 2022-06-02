package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.payment.PaymentRepository;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.PaymentDiscountService;
import com.kitcd.share_delivery_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final DeliveryRoomRepository deliveryRoomRepository;

    private final PaymentDiscountService paymentDiscountService;


    @Override
    public void enrollPayment(PaymentEnrollRequestDTO dto, List<MultipartFile> images, Long deliveryRoomId) {
        Optional<DeliveryRoom> room = deliveryRoomRepository.findById(deliveryRoomId);

        if(room.isEmpty()){
            throw new EntityNotFoundException("방이 존재하지 않습니다.");
        }

        Payment payment = paymentRepository.save(dto.toEntity(room.get()));

        List<PaymentDiscount> paymentDiscounts = paymentDiscountService.enrollPaymentDiscount(dto.getDiscounts(), payment);


    }
}
