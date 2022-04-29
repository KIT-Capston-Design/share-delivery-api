package com.kitcd.share_delivery_api.controller.auth;

import javax.validation.constraints.Pattern;

import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;
import com.kitcd.share_delivery_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    @Value("${open-api.naver-sms.activate}") private Boolean smsIsActivated;

    private final AuthService authService;

    @GetMapping("/verification-sms")
    public ResponseEntity<?> sendVerificationSMS(@RequestParam @Pattern(regexp = "^010[\\d]{8}") String phoneNumber){
        if(!smsIsActivated){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This api is deactivated.");
        }

        SMSResponseDTO responseDTO = authService.sendVerificationSMS(phoneNumber);

        //SENS로부터의 응답이 202이 아닐 경우 500
        int resStatusValue = responseDTO.getStatusCode();
        if(resStatusValue != HttpStatus.ACCEPTED.value()){
            resStatusValue = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        return ResponseEntity.status(resStatusValue).body(responseDTO);
    }
}
