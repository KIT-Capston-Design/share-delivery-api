package com.kitcd.share_delivery_api.controller.account;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;
import com.kitcd.share_delivery_api.dto.account.BankAccountDTO;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.service.impl.AccountServiceImpl;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


@Validated
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


    @PostMapping("/bank-account")
    public ResponseEntity<?> enrollMyBankAccount(BankAccountDTO bankAccountDTO) {

        Account account = accountService.saveMyBankAccount(bankAccountDTO.toEntity());

        return ResponseEntity.status(HttpStatus.OK).body(account.toDTO());

    }

    @GetMapping("/bank-account")
    public ResponseEntity<?> getMyBankAccount() {

        BankAccount bankAccount = accountService.getBankAccount(ContextHolder.getAccountId());

        return ResponseEntity.status(HttpStatus.OK).body(bankAccount.toDTO());

    }


}
