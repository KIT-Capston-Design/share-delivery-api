package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
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
    public void deliveryRoomCreate(DeliveryRoomEnrollRequestDTO dto, Account account) {

        ReceivingLocation receivingLocation = receivingLocationService.findByReceivingLocationId(dto.getReceivingLocationId());

        DeliveryRoom room = DeliveryRoom.builder()
                .content(dto.getContent())
                .receivingLocation(receivingLocation)
                .leader(account)
                .limitPerson(dto.getLimitPerson())
                .linkPlatformType(dto.getLinkPlatformType())
                .status(DeliveryRoomState.WAITING)
                .storeCategory(dto.getStoreCategory())
                .build();

        deliveryRoomRepository.save(room);

        List<OrderMenu> orderMenus = orderMenuService.enrollMainMenu(dto.getMenuList(), dto.getOptionList(), account, room.getDeliveryRoomId());

        entryOrderService.enrollEntryOrder(orderMenus, account, room, EntryOrderType.APPLIED);

    }
}
