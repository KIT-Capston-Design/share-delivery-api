package com.kitcd.share_delivery_api.domain.redis.deliveryroom;

import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationSMS;
import org.springframework.data.repository.CrudRepository;

public interface ActivatedDeliveryRoomInfoRedisRepository extends CrudRepository<ActivatedDeliveryRoomInfo, Long> {
    ActivatedDeliveryRoomInfo findByDeliveryRoomId(Long roomId);
}
