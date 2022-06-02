package com.kitcd.share_delivery_api.controller.payment;

import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/delivery-rooms")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{deliveryRoomId}/order-detail")
    public ResponseEntity enrollPayment(PaymentEnrollRequestDTO dto, List<MultipartFile> images, Long deliveryRoomId){

    }
}
