package com.kitcd.share_delivery_api.domain.redis.deliveryroom;


import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash(value = "auth:activated-delivery-room", timeToLive = 60*60*24*14) //ttl : 초 단위
public class ActivatedDeliveryRoomInfo {
    @Id
    @Indexed
    Long deliveryRoomId;
    String fcmGroupToken;
    List<Long> participatedUsers;

    @TimeToLive
    private Long ttl;
}
