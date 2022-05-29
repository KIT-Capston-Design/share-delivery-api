package com.kitcd.share_delivery_api.controller;

import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final DeliveryRoomService deliveryRoomService;

    @GetMapping("")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>(deliveryRoomService.getParticipantFCMTokens(1L), HttpStatus.OK);
    }
}
