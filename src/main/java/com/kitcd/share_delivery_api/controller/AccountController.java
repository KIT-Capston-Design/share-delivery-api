package com.kitcd.share_delivery_api.controller;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    @Value("${open-api.naver-sms.activate}") private Boolean smsIsActivated;
    private final AccountServiceImpl accountService;
    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<?> signUp(@Validated @RequestBody AccountRegistrationDTO dto) {

        // 휴대폰 인증 성공 시 DB에 계정 데이터 저장 후 저장된 계정 정보 반환
        //  휴대폰 인증 비활성화 되어 있을 경우 바로 회원가입 진행
        if (!smsIsActivated || authService.verifyCode(dto.getPhoneNumber(), dto.getVerificationCode(), VerificationType.SIGNUP)){

            Account account = accountService.signUp(dto);

            return new ResponseEntity<>(account, HttpStatus.CREATED);

        } else { // 실패 시 401

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Verification Failed");
        }


    }
}
