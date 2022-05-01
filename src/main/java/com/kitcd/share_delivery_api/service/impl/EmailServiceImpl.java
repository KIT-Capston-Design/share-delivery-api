package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationemail.VerificationEmail;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationemail.VerificationEmailRedisRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationemail.VerificationType;
import com.kitcd.share_delivery_api.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final VerificationEmailRedisRepository verificationEmailRedisRepository;
    private final SecureRandom secureRandom;
    private final AccountRepository accountRepository;
    private final static int BOUNDARY = 999999;


    @Override
    @Async
    public void sendEmail(String email) throws EntityExistsException{
        if(accountRepository.findByEmail(email) != null){
            throw new EntityExistsException("이메일이 존재합니다.");
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); //메일 틀 만들기

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("공유배달 이메일 인증");
        StringBuilder sb = new StringBuilder();
        sb.append("인증번호 : ");
        String secureNum = String.format("%06d", secureRandom.nextInt(BOUNDARY));
        sb.append(secureNum);

        verificationEmailRedisRepository.save(new VerificationEmail(email, secureNum, VerificationType.VEREMAIL)); //레디스에 자동 저장
        simpleMailMessage.setText(sb.toString());
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public boolean verificationEmail(String email, String authNum){
        VerificationEmail verificationEmail = verificationEmailRedisRepository.findVerificationEmailByEmail(email);
        if(verificationEmail == null){
            return false;
        }
        if(authNum.equals(verificationEmail.getVerificationCode())){
            return false;
        }
        return true;
    }

    @Override
    public Account AccountAddEmail(String phoneNum, String email){

        Account user = accountRepository.findByPhoneNumber(phoneNum);
        user.addEmail(email);
        accountRepository.save(user);
        return user;
    }
}
