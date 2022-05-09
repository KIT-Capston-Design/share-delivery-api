package com.kitcd.share_delivery_api.controller.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomEnrollRequestDTO;
import com.kitcd.share_delivery_api.security.service.AccountContext;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-rooms")
public class DeliveryRoomController {

    private final DeliveryRoomService deliveryRoomService;
    private final ReceivingLocationService receivingLocationService;


    @GetMapping("")
    public ResponseEntity<?> getDeliveryRooms(@RequestParam(name = "lat") @NotBlank Double latitude, @RequestParam(name = "lng") @NotBlank Double longitude) {

        Location location = Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        deliveryRoomService.getDeliveryRooms(location, 1.5);


        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("")
    public ResponseEntity<?> enrollDeliveryRoom(@RequestBody DeliveryRoomEnrollRequestDTO dto){

        try {
            ReceivingLocation receivingLocation = receivingLocationService.findByReceivingLocationId(dto.getReceivingLocationId());

            deliveryRoomService.deliveryRoomCreate(dto.toEntity(ContextHolder.getAccount(), receivingLocation), dto.getMenuList());

            return ResponseEntity.status(HttpStatus.OK).body(null);

        } catch (EntityNotFoundException enfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());

        }

    }

}
