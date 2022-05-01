package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;

import java.util.List;

public interface DeliveryRoomService {

    List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance);
}
