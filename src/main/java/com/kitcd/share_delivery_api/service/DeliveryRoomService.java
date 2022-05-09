package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomEnrollRequestDTO;

public interface DeliveryRoomService {
    void deliveryRoomCreate(DeliveryRoomEnrollRequestDTO dto, Account account);
    List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance);
}
