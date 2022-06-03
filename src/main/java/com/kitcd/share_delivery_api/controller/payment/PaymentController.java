package com.kitcd.share_delivery_api.controller.payment;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.payment.PaymentEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import com.kitcd.share_delivery_api.service.PaymentService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

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
    public ResponseEntity enrollPayment(@RequestPart(value = "orderDetail")PaymentEnrollRequestDTO dto, @RequestParam(value = "orderFormList")List<MultipartFile> images, @PathVariable Long deliveryRoomId){
        DeliveryRoom room = deliveryRoomService.findByDeliveryRoomId(deliveryRoomId);

        //방장인지 체크
        room.checkLeader(ContextHolder.getAccountId());

        //주문정보 등록
        paymentService.enrollPayment(dto, images, room);

        //그룹키 가져오고
        ActivatedDeliveryRoomInfo activatedDeliveryRoomInfo = activatedDeliveryRoomInfoRedisRepository.findByDeliveryRoomId(deliveryRoomId);


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

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
