package com.kitcd.share_delivery_api.controller.auth;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.account.AccountEmailAddDTO;
import com.kitcd.share_delivery_api.security.service.AccountContext;
import com.kitcd.share_delivery_api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class EmailController {

    private final EmailService emailService;


    @GetMapping("/verification-email")
    public ResponseEntity<?> sendVerificationEmail(@RequestParam @Email String email) {
        try {
            emailService.sendEmail(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("성공적으로 인증메일이 전송되었습니다.");
        }catch (EntityExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일이 존재합니다.");
        }
    }

    @PostMapping("/verification-email")
    public ResponseEntity<?> EmailEnrollWithVerificationNum(@RequestBody AccountEmailAddDTO accountEmailAddDTO){
        if(emailService.verificationEmail(accountEmailAddDTO.getEmail(), accountEmailAddDTO.getVerificationNum())){
            emailService.AccountAddEmail(getAccount().getPhoneNumber(), accountEmailAddDTO.getEmail());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("성공적으로 이메일이 등록되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
    }

    private Account getAccount() {
        AccountContext userData = (AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account user = userData.getAccount();
        return user;
    }
}
