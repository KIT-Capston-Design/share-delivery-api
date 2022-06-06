package com.kitcd.share_delivery_api.controller.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.*;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

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


    @GetMapping("delivery-rooms/{deliveryRoomId}/completed-delivery")
    public ResponseEntity<?> deliveryComplete(@PathVariable Long deliveryRoomId){
        DeliveryRoomState deliveryRoomState = deliveryRoomService.deliveryComplete(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoomState);
    }

    @GetMapping("delivery-rooms/{deliveryRoomId}/order-detail")
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
    public ResponseEntity<?> rejectOrder(@RequestParam(name = "userId") @NotNull Long accountId, @RequestParam(name = "roomId") @NotNull Long roomId) {

        DeliveryRoom deliveryRoom = deliveryRoomService.findByDeliveryRoomId(roomId);
        deliveryRoom.checkLeader(ContextHolder.getAccountId());
        entryOrderService.rejectEntryOrder(accountId, deliveryRoom);


        // 거절된 참여자에게 push 알림 전송
        Map<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.ORDER_REJECTED);
        data.put("roomId", roomId);


        firebaseCloudMessageService.sendMessageTo(
                loggedOnInformationService.getFcmTokenByAccountId(accountId),
                deliveryRoom.getContent() + " 방 입장이 거절되었습니다.",
                "null",
                data
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

        Long myId = ContextHolder.getAccountId();

        DeliveryRoom room = deliveryRoomService.findByDeliveryRoomId(deliveryRoomId);

        //자신이 리더일 경우 참여할 수 없으므로 예외 발생
        if(Objects.equals(myId, room.getLeader().getAccountId())) throw new IllegalStateException("이미 참여 중 입니다.");

        //이미 참여 신청한 경우 참여할 수 없으므로 예외 발생
        List<Long> participantsIds = deliveryRoomService.getParticipantsIds(deliveryRoomId, State.PENDING); //
        for(Long id : participantsIds)
            if (Objects.equals(id, myId)) throw new IllegalStateException("이미 참여 중 입니다.");

        //참여 절차 시작
        entryOrderService.enrollEntryOrder(room, dtos, EntryOrderType.PARTICIPATION, State.PENDING);

        // 방의 주도자에게 참가 신청 알람 전송.
        Map<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.ENTRY_ORDERS);
        data.put("roomId", deliveryRoomId);

        firebaseCloudMessageService.sendMessageTo(
                loggedOnInformationService.getFcmTokenByAccountId(room.getLeader().getAccountId()),
                room.getContent() + " 방에 새로운 참가 신청이 있습니다.",
                ContextHolder.getAccount().getNickname()+"님이 참가 신청 하셨습니다. 메뉴와 금액을 확인해주세요.",
                data
        );

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoomId);
    }

    @GetMapping("delivery-rooms/{deliveryRoomId}/close-recruit")
    public ResponseEntity<?> closeRecruit(@PathVariable Long deliveryRoomId){

        DeliveryRoom deliveryRoom = deliveryRoomService.closeRecruit(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRoom.getDeliveryRoomId());

    }

}
