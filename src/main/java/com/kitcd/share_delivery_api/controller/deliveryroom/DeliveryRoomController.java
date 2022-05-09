package com.kitcd.share_delivery_api.controller.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomEnrollRequestDTO;
import com.kitcd.share_delivery_api.security.service.AccountContext;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kitcd.share_delivery_api.controller.common.CurrentLoggedInSession.getAccount;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class DeliveryRoomController {

    private final DeliveryRoomService deliveryRoomService;
    @PostMapping("/delivery-rooms")
    public ResponseEntity<?> enrollDeliveryRoom(@RequestBody DeliveryRoomEnrollRequestDTO dto){
        deliveryRoomService.deliveryRoomCreate(dto, getAccount());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
