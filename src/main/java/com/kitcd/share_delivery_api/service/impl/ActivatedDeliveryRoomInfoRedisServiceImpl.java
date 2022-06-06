package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.service.ActivatedDeliveryRoomInfoRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ActivatedDeliveryRoomInfoRedisServiceImpl implements ActivatedDeliveryRoomInfoRedisService {

    private final ActivatedDeliveryRoomInfoRedisRepository activatedDeliveryRoomInfoRedisRepository;

    @Override
    public String getGroupFcmToken(Long deliveryRoomId){
        ActivatedDeliveryRoomInfo activatedDeliveryRoomInfo = findById(deliveryRoomId);

        return activatedDeliveryRoomInfo.getFcmGroupToken();
    }

    @Override
    public ActivatedDeliveryRoomInfo findById(Long deliveryRoomId){

        Optional<ActivatedDeliveryRoomInfo> obj = activatedDeliveryRoomInfoRedisRepository.findById(deliveryRoomId);

        if(obj.isEmpty()) throw new FetchNotFoundException(ActivatedDeliveryRoomInfo.class.toString(), deliveryRoomId);

        return obj.get();
    }

}
