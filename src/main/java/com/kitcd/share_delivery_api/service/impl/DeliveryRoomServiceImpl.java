package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
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
@RequiredArgsConstructor
@Service
@Transactional
public class DeliveryRoomServiceImpl implements DeliveryRoomService {
    private final DeliveryRoomRepository deliveryRoomRepository;
    private final OrderMenuService orderMenuService;
    private final ReceivingLocationService receivingLocationService;
    private final EntryOrderService entryOrderService;

    @Override
    public void deliveryRoomCreate(DeliveryRoomEnrollRequestDTO dto, Account account) {
        Coordinate location = new Coordinate(dto.getReceivingLocation().getLat(), dto.getReceivingLocation().getLng());

        ReceivingLocation receivingLocation = receivingLocationService.getReceivingLocationByNameAndCoordinate(dto.getReceivingLocation().getDescription(), location);



        DeliveryRoom room = DeliveryRoom.builder()
                .content(dto.getContent())
                .receivingLocation(receivingLocation) //저장한다음 연관관계 매핑
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
