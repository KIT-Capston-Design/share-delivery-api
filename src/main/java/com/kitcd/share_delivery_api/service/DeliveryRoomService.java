package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;

import java.util.List;

public interface DeliveryRoomService {
    DeliveryRoom deliveryRoomCreate(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList);
    List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance);

    List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId);

    DeliveryRoom findByLeaderId(Long accountId);

    DeliveryRoom findByDeliveryRoomId(Long roomId);
}
