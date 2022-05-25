package com.kitcd.share_delivery_api.domain.redis.deliveryroom;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash(value = "auth:activated-delivery-room", timeToLive = 60*60*24*14) //ttl : 초 단위
public class ActivatedDeliveryRoomInfo {
    Long deliveryRoomId;
    String fcmGroupToken;
}
