package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;

public interface ActivatedDeliveryRoomInfoRedisService {
    String getGroupFcmToken(Long deliveryRoomId);

    ActivatedDeliveryRoomInfo findById(Long deliveryRoomId);
}
