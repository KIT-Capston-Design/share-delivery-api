package com.kitcd.share_delivery_api.controller.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.*;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.fcm.FCMGroupRequest;
import com.kitcd.share_delivery_api.dto.payment.FinalOrderInformationDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.*;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class DeliveryRoomController {

    private final DeliveryRoomService deliveryRoomService;
    private final ReceivingLocationService receivingLocationService;
    private final StoreCategoryService storeCategoryService;
    private final EntryOrderService entryOrderService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final LoggedOnInformationService loggedOnInformationService;
    private final ActivatedDeliveryRoomInfoRedisRepository activatedDeliveryRoomInfoRedisRepository;


    @GetMapping("/api/delivery-rooms/{deliveryRoomId}/order-detail")
    public ResponseEntity<?> getFinalOrderInformation(@PathVariable Long deliveryRoomId) {

        FinalOrderInformationDTO result = deliveryRoomService.getFinalOrderInformation(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("delivery-rooms/{deliveryRoomId}/exit-room")
    public ResponseEntity<?> exitDeliveryRoom(@PathVariable Long deliveryRoomId) {

        Long result = deliveryRoomService.exitDeliveryRoom(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("delivery-rooms/{deliveryRoomId}")
    public ResponseEntity<?> deleteDeliveryRoom(@PathVariable Long deliveryRoomId) {

        deliveryRoomService.deleteDeliveryRoom(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoomId);
    }



    @GetMapping("delivery-rooms/orders-reject")
    public ResponseEntity<?> rejectOrder(@RequestParam(name = "userId") @NotNull Long userId, @RequestParam(name = "roomId") @NotNull Long roomId) {

        DeliveryRoom deliveryRoom = deliveryRoomService.findByDeliveryRoomId(roomId);
        deliveryRoom.checkLeader(ContextHolder.getAccountId());
        entryOrderService.rejectEntryOrder(userId, deliveryRoom);

        // 거절된 참여자에게 push 알림 전송
        firebaseCloudMessageService.sendMessageTo(
                loggedOnInformationService.getFcmTokenByAccountId(ContextHolder.getAccountId()),
                deliveryRoom.getContent() + " 방 입장이 거절되었습니다.",
                "null",
                null
        );

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




    @GetMapping("delivery-history")
    public ResponseEntity<?> getDeliveryHistory(){

        List<ParticipatedDeliveryRoomDTO> deliveryHistories = deliveryRoomService.getDeliveryHistory(ContextHolder.getAccount().getAccountId());

        return ResponseEntity.status(HttpStatus.OK).body(deliveryHistories);
    }

    @GetMapping("delivery-rooms/{deliveryRoomId}")
    public ResponseEntity<?> getDeliveryRoom(@PathVariable Long deliveryRoomId) {

        DeliveryRoomDTO deliveryRoom = deliveryRoomService.getDeliveryRoomDTO(deliveryRoomId);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoom);

    }


    @GetMapping("delivery-rooms")
    public ResponseEntity<?> getDeliveryRooms(@RequestParam @NotNull Double latitude, @RequestParam @NotNull Double longitude, @RequestParam(name = "radius") @NotNull Double radius) {

        Location location = Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        List<DeliveryRoomDTO> deliveryRooms = deliveryRoomService.getDeliveryRooms(location, radius);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRooms);
    }


    @PostMapping("delivery-rooms")
    public ResponseEntity<?> enrollDeliveryRoom(@RequestBody DeliveryRoomEnrollRequestDTO dto){

        ReceivingLocation receivingLocation = receivingLocationService.findByReceivingLocationId(dto.getReceivingLocationId());

        StoreCategory storeCategory = storeCategoryService.findStoreCategoryWithName(dto.getStoreCategory());

        DeliveryRoom room = deliveryRoomService.deliveryRoomCreate(dto.toEntity(ContextHolder.getAccount(), receivingLocation, storeCategory), dto.getMenuList());

        return ResponseEntity.status(HttpStatus.OK).body(room.toDTO());

    }

    @PostMapping("delivery-rooms/{deliveryRoomId}/entry-orders")
    public ResponseEntity<?> requestJoinDeliveryRoom(@PathVariable Long deliveryRoomId, @RequestBody @NotEmpty List<OrderMenuRequestDTO> dtos){

        DeliveryRoom room = deliveryRoomService.findByDeliveryRoomId(deliveryRoomId);

        entryOrderService.enrollEntryOrder(room, dtos, EntryOrderType.PARTICIPATION, State.PENDING);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoomId);
    }

    @GetMapping("delivery-rooms/{deliveryRoomId}/close-recruit")
    public ResponseEntity<?> closeRecruit(@PathVariable Long deliveryRoomId){

        DeliveryRoom deliveryRoom = deliveryRoomService.closeRecruit(deliveryRoomId);

        //클라이언트가 방장인지 체크. 방장이 아닐 경우 AccessDeniedException
        deliveryRoom.checkLeader(ContextHolder.getAccountId());

        //  파이어베이스에 FCM 그룹 생성 요청 보내고 그룹 토큰 반환받는다. // throwable JSONException, IOException
        String fcmGroupToken = firebaseCloudMessageService.sendGroupRequest(
                FCMGroupRequest.Type.create,
                "DeliveryRoom_" + deliveryRoomId.toString(),
                null, //생성 시에는 그룹 키 null로 전송
                deliveryRoomService.getParticipantFCMTokens(deliveryRoomId, State.ACCEPTED) //모집글에 참여한 유저 토큰들 받아서 넘겨주기
        );

        //그룹 토큰 통해 해당 모집글 참여자들에게 메시지 전송
        Map<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.CLOSE_RECRUIT);
        data.put("roomId", deliveryRoomId);
        firebaseCloudMessageService.sendMessageTo(
                fcmGroupToken, "해당 모집글의 인원 모집이 종료되었습니다. 이제 주문이 시작됩니다.", deliveryRoom.getContent(), data
        );

        //redis에 '모집글 - 그룹fcm토큰' 형식으로 저장
        activatedDeliveryRoomInfoRedisRepository.save(
                ActivatedDeliveryRoomInfo.builder()
                        .deliveryRoomId(deliveryRoomId)
                        .fcmGroupToken(fcmGroupToken)
                        .users(deliveryRoomService.getParticipantsIds(deliveryRoomId, State.ACCEPTED))
                        .build()
        );

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoom.getDeliveryRoomId());

    }

}
