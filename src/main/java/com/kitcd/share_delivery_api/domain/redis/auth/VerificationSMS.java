package com.kitcd.share_delivery_api.domain.redis.auth;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@NoArgsConstructor
@Getter
@RedisHash(value = "auth:verification-sms", timeToLive = 60*3) //ttl : 초 단위
public class VerificationSMS {

    private static final String MESSAGE_PREFIX = "[share-delivery]";

    @Id @Indexed
    private String phoneNumber;
    private String verificationCode;
    private VerificationType verificationType;
    public VerificationSMS(String phoneNumber, String verificationCode, Account account) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;

        if(account != null) // 계정이 존재하면 로그인, 존재하지 않으면 회원가입
            verificationType = VerificationType.LOGIN;
        else
            verificationType = VerificationType.SIGNUP;
    }

    public String getMessage(){
        return MESSAGE_PREFIX + " " + verificationType.toString() + " 인증번호입니다. [" + verificationCode + "]";
    }

}
