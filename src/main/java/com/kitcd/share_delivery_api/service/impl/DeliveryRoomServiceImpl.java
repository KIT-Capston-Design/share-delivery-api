package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.service.EntryOrderService;
import com.kitcd.share_delivery_api.service.OrderMenuService;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
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

    private final DeliveryRoomRepository deliveryRoomRepository;
    private final OrderMenuService orderMenuService;
    private final ReceivingLocationService receivingLocationService;
    private final EntryOrderService entryOrderService;


    public List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance){
        return deliveryRoomRepository.findDeliveryRoomDTOWithLocation(location, distance);
    }


    @Override
    public DeliveryRoom deliveryRoomCreate(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList) {

        DeliveryRoom room = deliveryRoomRepository.save(deliveryRoom);

        entryOrderService.enrollEntryOrder(room, menuList, EntryOrderType.APPLIED);

        return room;
    }
}
