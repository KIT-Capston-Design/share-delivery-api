package com.kitcd.share_delivery_api.controller.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformationRedisRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.*;
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


import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

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


    //TODO
    // 스프링 시큐리티 어노테이션 통해 주도자의 요청인지 확인 필요
    @GetMapping("delivery-rooms/orders-reject")
    public ResponseEntity<?> rejectOrder(@RequestParam(name = "userId") @NotNull Long userId, @RequestParam(name = "roomId") @NotNull Long roomId) {

        try{
            DeliveryRoom deliveryRoom = deliveryRoomService.findByDeliveryRoomId(roomId);

            entryOrderService.rejectEntryOrder(userId, deliveryRoom);

            // 거절된 참여자에게 push 알림 전송
            firebaseCloudMessageService.sendMessageTo(
                    loggedOnInformationService.getFcmTokenByAccountId(ContextHolder.getAccountId()),
                    deliveryRoom.getContent() + " 방 입장이 거절되었습니다.",
                    "null"
            );

        }catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage() + " is not found");

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




    @GetMapping("delivery-history")
    public ResponseEntity<?> getDeliveryHistory(){

        List<ParticipatedDeliveryRoomDTO> deliveryHistories = deliveryRoomService.getDeliveryHistory(ContextHolder.getAccount().getAccountId());

        return ResponseEntity.status(HttpStatus.OK).body(deliveryHistories);
    }

    @GetMapping("delivery-rooms")
    public ResponseEntity<?> getDeliveryRooms(@RequestParam(name = "lat") @NotNull Double latitude, @RequestParam(name = "lng") @NotNull Double longitude, @RequestParam(name = "radius") @NotNull Double radius) {

        Location location = Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        List<DeliveryRoomDTO> deliveryRooms = deliveryRoomService.getDeliveryRooms(location, radius);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryRooms);
    }


    @PostMapping("delivery-rooms")
    public ResponseEntity<?> enrollDeliveryRoom(@RequestBody DeliveryRoomEnrollRequestDTO dto){

        try {
            ReceivingLocation receivingLocation = receivingLocationService.findByReceivingLocationId(dto.getReceivingLocationId());

            StoreCategory storeCategory = storeCategoryService.findStoreCategoryWithName(dto.getStoreCategory());

            DeliveryRoom room = deliveryRoomService.deliveryRoomCreate(dto.toEntity(ContextHolder.getAccount(), receivingLocation, storeCategory), dto.getMenuList());

            return ResponseEntity.status(HttpStatus.OK).body(
                    DeliveryRoomEnrollResponseDTO
                            .builder()
                            .roomId(room.getDeliveryRoomId())
                            .build()
            );

        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());

        }

    }

    @PostMapping("delivery-rooms/{deliveryRoomId}/entry-orders")
    public ResponseEntity<?> requestJoinDeliveryRoom(@PathVariable Long deliveryRoomId, @RequestBody JoinRequestDeliveryRoomDTO dto){
        try{
            DeliveryRoom room = deliveryRoomService.findByDeliveryRoomId(deliveryRoomId);

            if(!(room.getPeopleNumber() < room.getLimitPerson())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인원이 초과되어 참여할 수 없습니다.");
            }

            entryOrderService.enrollEntryOrder(room, dto.getMenuList(), EntryOrderType.PARTICIPATION, State.PENDING);

            // 방의 주도자에게 참가 신청 알람 전송.
            firebaseCloudMessageService.sendMessageTo(
                    loggedOnInformationService.getFcmTokenByAccountId(room.getLeader().getAccountId()),
                    room.getContent() + " 방에 새로운 참가 신청이 있습니다.",
                    "null"
            );

            return ResponseEntity.status(HttpStatus.OK).body(null);

        }catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(enfe.getMessage());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
