package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryRoomServiceImpl implements DeliveryRoomService {

    private DeliveryRoomRepository deliveryRoomRepository;

    public List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance){
        return deliveryRoomRepository.findDeliveryRoomDTOWithLocation(location, distance);
    }

}
