package com.kitcd.share_delivery_api.domain.redis.auth.verificationemail;


import com.kitcd.share_delivery_api.domain.redis.auth.verificationemail.VerificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
@RedisHash(value = "auth:verification-sms", timeToLive = 60*3) //ttl : 초 단위
public class VerificationEmail {
    @Id @Indexed
    private String email;
    private String verificationCode;
    private VerificationType verificationType;

    public VerificationEmail(String email, String verificationCode, VerificationType verificationType){
        this.email = email;
        this.verificationCode = verificationCode;
        this.verificationType = verificationType;
    }

}
