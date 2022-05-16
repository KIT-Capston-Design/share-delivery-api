package com.kitcd.share_delivery_api.controller.order;


import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.EntryOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-rooms/")
public class OrderController {

    private final DeliveryRoomService deliveryRoomService;
    private final EntryOrderService entryOrderService;


    //TODO : auth : 참여자만 조회 가능하도록 설정 필요해보임
    @GetMapping("orders/{deliveryRoomId}")
    public ResponseEntity<?> getOrders(@PathVariable @NotNull Long deliveryRoomId) {

        try{
            List<OrderResDTO> orders = entryOrderService.getOrderInformation(deliveryRoomId);
            return ResponseEntity.status(HttpStatus.OK).body(orders);


        }catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage() + " is not found");

        }

    }

}
