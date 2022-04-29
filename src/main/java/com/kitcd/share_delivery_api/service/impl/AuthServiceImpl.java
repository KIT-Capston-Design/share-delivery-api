package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.redis.auth.VerificationSMS;
import com.kitcd.share_delivery_api.domain.redis.auth.VerificationSMSRedisRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.VerificationType;
import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.service.SENSService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final SENSService sensService;
    private final AccountService accountService;
    private final SecureRandom secureRandom;
    private final VerificationSMSRedisRepository verificationSMSRedisRepository;

    public SMSResponseDTO sendVerificationSMS(String phoneNumber) {

        VerificationSMS sms = new VerificationSMS(
                phoneNumber,
                String.format("%06d", secureRandom.nextInt(999999)),
                accountService.findByPhoneNumber(phoneNumber)
        ); // 폰번호, 의사난수, 로그인 위한 인증메일인지 회원가입 위한 인증메일인지 판단 위해 db에서 계정 select

        SMSResponseDTO responseDTO = sensService.sendSMS(sms.getPhoneNumber(), sms.getMessage()); // 네이버 api sms발송 요청

        responseDTO.setPhoneNumber(sms.getPhoneNumber());
        responseDTO.setVerificationType(sms.getVerificationType());

        // 발송 성공 시 redis에 인증 위한 정보 등록
        if(HttpStatus.ACCEPTED.value() == responseDTO.getStatusCode()){
            verificationSMSRedisRepository.save(sms);
        }

        return responseDTO;
    }

    // true : 성공, false : 실패
    public Boolean verifyCode(String phoneNumber, String code, VerificationType verificationType){

        VerificationSMS sms = verificationSMSRedisRepository.findVerificationSMSByPhoneNumber(phoneNumber);

        if(sms == null)
            return false;

        //인증 타입과 코드 동일한지 검증
        return sms.getVerificationType().equals(verificationType) && sms.getVerificationCode().equals(code);
    }

}
