package com.kitcd.share_delivery_api.domain.redis.auth.verificationsms;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;


@NoArgsConstructor
@Getter
@RedisHash(value = "auth:verification-sms") //ttl : 초 단위
public class VerificationSMS {

    private static final String MESSAGE_PREFIX = "[share-delivery]";

    @Id @Indexed
    private String phoneNumber;
    private String verificationCode;
    private VerificationType verificationType;

    @TimeToLive
    private Long ttl;

    public VerificationSMS(String phoneNumber, String verificationCode, Account account) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;

        if(account != null) // 계정이 존재하면 로그인, 존재하지 않으면 회원가입
            this.verificationType = VerificationType.LOGIN;
        else
            this.verificationType = VerificationType.SIGNUP;

        this.ttl = 60L * 3; // 초 단위; 3분
    }

    public String getMessage(){
        return MESSAGE_PREFIX + " " + verificationType.toString() + " 인증번호입니다. [" + verificationCode + "]";
    }

    // 회원가입 후 로그인 위한 타입 변경
    public void typeUpdateForLogin(){
        if(verificationType == VerificationType.SIGNUP)
            verificationType = VerificationType.LOGIN;

        this.ttl = 10L; // 클라이언트에서 회원가입 성공 응답 받으면 바로 로그인 api 요청 보낼 것이므로 ttl 최대 10초로 제한
    }
}
