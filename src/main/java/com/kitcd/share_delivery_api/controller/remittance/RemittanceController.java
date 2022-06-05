package com.kitcd.share_delivery_api.controller.remittance;


import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO;
import com.kitcd.share_delivery_api.service.PaymentService;
import com.kitcd.share_delivery_api.service.RemittanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class RemittanceController {

    private final RemittanceService remittanceService;
    private final PaymentService paymentService;


    @GetMapping("delivery-rooms/{deliveryRoomId}/remittances")
    public ResponseEntity<?> getRemittances(@PathVariable Long deliveryRoomId){

        List<RemittanceDTO> remittances = remittanceService.getRemittanceDTOsByDeliveryRoomId(deliveryRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(remittances);
    }
}
