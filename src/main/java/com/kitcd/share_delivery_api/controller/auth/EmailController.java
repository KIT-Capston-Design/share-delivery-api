package com.kitcd.share_delivery_api.controller.auth;


import com.kitcd.share_delivery_api.dto.account.AccountEmailAddDTO;
import com.kitcd.share_delivery_api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.Email;

import static com.kitcd.share_delivery_api.utils.ContextHolder.getAccount;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class EmailController {

    private final EmailService emailService;


    @GetMapping("/verification-email")
    public ResponseEntity<?> sendVerificationEmail(@RequestParam @Email String email) {
        emailService.sendEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("성공적으로 인증메일이 전송되었습니다.");

    }

    @PostMapping("/verification-email")
    public ResponseEntity<?> EmailEnrollWithVerificationNum(@RequestBody AccountEmailAddDTO accountEmailAddDTO){
        if(emailService.verificationEmail(accountEmailAddDTO.getEmail(), accountEmailAddDTO.getVerificationNum())){
            emailService.AccountAddEmail(getAccount().getPhoneNumber(), accountEmailAddDTO.getEmail());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("성공적으로 이메일이 등록되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
    }

}
