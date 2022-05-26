package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;

import java.util.List;

public interface DeliveryRoomRepositoryCustom {
    List<DeliveryRoomDTO> findDeliveryRoomDTOWithLocation(Location location, Double radius);

}
