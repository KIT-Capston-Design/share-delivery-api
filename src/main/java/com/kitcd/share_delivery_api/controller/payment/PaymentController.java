package com.kitcd.share_delivery_api.controller.payment;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import com.kitcd.share_delivery_api.service.PaymentService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;
    private final ActivatedDeliveryRoomInfoRedisRepository activatedDeliveryRoomInfoRedisRepository;

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    private final DeliveryRoomService deliveryRoomService;

    @PostMapping("/delivery-rooms/{deliveryRoomId}/order-detail")
    public ResponseEntity<?> enrollPayment(@Validated @RequestPart(value = "orderDetail")PaymentEnrollRequestDTO dto, @RequestParam(value = "orderFormList")List<MultipartFile> images, @PathVariable Long deliveryRoomId){
        DeliveryRoom room = deliveryRoomService.findByDeliveryRoomId(deliveryRoomId);

        //주문정보 등록
        DeliveryRoomState result = paymentService.enrollPayment(dto, images, room);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
