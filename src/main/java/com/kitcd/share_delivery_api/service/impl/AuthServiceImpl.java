package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;
import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformationRedisRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationSMS;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationSMSRedisRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;
import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.service.LoggedOnInformationService;
import com.kitcd.share_delivery_api.service.SENSService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final LoggedOnInformationService loggedOnInformationService;


    public void saveLoggedOnInformation(LoggedOnInformation loggedOnInformation){
        loggedOnInformationService.save(loggedOnInformation);
    }

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

        //인증번호를 요청한 이력이 없을 경우 인증 실패 (false 리턴)
        if(sms == null)
            return false;

        //인증 타입과 코드가 동일한지 검증
        Boolean result = sms.getVerificationType().equals(verificationType) && sms.getVerificationCode().equals(code);

        //인증 성공 시
        // 로그인의 경우 레디스에서 인증번호 데이터 삭제
        // 회원가입의 경우 로그인도 동일한 인증번호로 수행 할 것이므로 레디스에 저장된 VerificationSMS 타입 LOGIN으로 변경
        if(result) {
            if (VerificationType.LOGIN.equals(sms.getVerificationType())) {
                verificationSMSRedisRepository.delete(sms);
            } else if (VerificationType.SIGNUP.equals(sms.getVerificationType())) {
                sms.typeUpdateForLogin(); // typeUpdateForLogin() : 타입 SIGNUP 에서 LOGIN으로 변경
                verificationSMSRedisRepository.save(sms);
            }
        }

        return result;
    }

}
