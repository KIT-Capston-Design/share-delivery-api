package com.kitcd.share_delivery_api.controller.order;


import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.EntryOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("orders/{deliveryRoomId}")
    public ResponseEntity<?> getOrderInformation(@PathVariable @NotNull Long deliveryRoomId) {

        List<OrderResDTO> orders = entryOrderService.getOrderInformation(deliveryRoomId, State.PENDING);
        orders.addAll(entryOrderService.getOrderInformation(deliveryRoomId, State.ACCEPTED));

        return ResponseEntity.status(HttpStatus.OK).body(orders);

    }

}
