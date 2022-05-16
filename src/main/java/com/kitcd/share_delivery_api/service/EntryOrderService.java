package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;

import java.util.List;

public interface EntryOrderService {
    EntryOrder enrollEntryOrder(DeliveryRoom room, List<OrderMenuRequestDTO> menus, EntryOrderType entryOrderType);

    EntryOrder findByAccountIdAndDeliveryRoomId(Long accountId, Long deliveryRoomId);

    void rejectEntryOrder(Long userId, DeliveryRoom deliveryRoom) throws Exception;

    List<OrderResDTO> getOrderInformation(Long deliveryRoomId);
}
