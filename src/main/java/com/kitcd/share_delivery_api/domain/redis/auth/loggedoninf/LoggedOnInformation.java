package com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash(value = "auth:logged-in-user", timeToLive = 60*43200) //ttl : 초 단위
public class LoggedOnInformation {

    @Id  @Indexed
    private Long accountId;
    private String phoneNumber;
    private String fcmToken;
    private String refreshToken;

}
