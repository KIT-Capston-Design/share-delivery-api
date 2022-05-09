package com.kitcd.share_delivery_api.controller.receivinglocation;


import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kitcd.share_delivery_api.controller.common.CurrentLoggedInSession.getAccount;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReceivingLocationController {

    private final ReceivingLocationService receivingLocationService;

    @PostMapping("/api/receiving-location")
    public ResponseEntity<?> createReceivingLocation(@RequestBody ReceivingLocationDTO dto){
        ReceivingLocationDTO data = receivingLocationService.enrollReceivingLocation(getAccount(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(data); //같은 Coordinate와 같은 description이 같은 아이디에 있어도 여러개 만들 수 있게.
    }
}
