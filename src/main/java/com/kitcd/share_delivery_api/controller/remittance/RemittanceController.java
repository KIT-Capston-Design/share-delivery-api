package com.kitcd.share_delivery_api.controller.remittance;


import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.PaymentService;
import com.kitcd.share_delivery_api.service.RemittanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RemittanceController {

    private final RemittanceService remittanceService;
    private final DeliveryRoomService deliveryRoomService;
    private final PaymentService paymentService;

    @GetMapping("/delivery-rooms/{deliveryRoomId}/remittances/{remittanceId}/approve")
    public ResponseEntity<?> approveRemittance(@PathVariable Long deliveryRoomId, @PathVariable Long remittanceId){

        DeliveryRoom deliveryRoom = deliveryRoomService.findByDeliveryRoomId(deliveryRoomId);

        //result는 연산 후의 isRemitted value
        boolean result = remittanceService.approveRemittance(remittanceId, deliveryRoom);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/delivery-rooms/{deliveryRoomId}/remittances")
    public ResponseEntity<?> getRemittances(@PathVariable Long deliveryRoomId){

        List<RemittanceDTO> remittances = remittanceService.getRemittanceDTOsByDeliveryRoomId(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(remittances);
    }
}
