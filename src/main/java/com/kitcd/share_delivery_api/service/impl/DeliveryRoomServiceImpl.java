package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderTableRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenuRepository;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DeliveryRoomServiceImpl implements DeliveryRoomService {
    private DeliveryRoomRepository deliveryRoomRepository;
    private OrderMenuRepository orderMenuRepository;
    private ReceivingLocationRepository receivingLocationRepository;
    private EntryOrderTableRepository entryOrderTableRepository;

    @Override
    public void deliveryRoomCreate(DeliveryRoomEnrollRequestDTO dto, Account account) {
        //일단 급하니 차후에 다시 리펙토링 하겠슴니다....
        Coordinate location = new Coordinate(dto.getReceivingLocation().getLat(), dto.getReceivingLocation().getLng());

        ReceivingLocation receivingLocation = receivingLocationRepository.findByNameAndCoordinate(dto.getReceivingLocation().getDescription(), location);

        if(receivingLocation == null) {
            receivingLocation = ReceivingLocation.builder()
                    .account(account)
                    .coordinate(location)
                    .build();
            receivingLocationRepository.save(receivingLocation); //저장...
        }

        List<OrderMenu> orderMenus = dto.getMenuList().stream().map(i -> OrderMenu.builder() // orderMenu 부모 먼저 작성
                .amount(i.getAmount())
                .menuName(i.getMenuName())
                .build()).collect(Collectors.toList());

        orderMenuRepository.saveAll(orderMenus);

        List<OrderMenu> optionMenus = dto.getOptionList().stream().map(i -> OrderMenu.builder() //옵션들 추가
                .menuName(i.getOptionName())
                .amount(i.getAmount())
                .parentMenu(orderMenus.stream().filter(j -> j.getMenuName().equals(i.getParent())).findAny().orElse(null))
                .build()).collect(Collectors.toList());

        orderMenuRepository.saveAll(optionMenus);

        orderMenus.addAll(optionMenus); // 두 개의 리스트 합치기

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

        EntryOrder leaderOrder = EntryOrder.builder()
                .orderMenus(orderMenus)
                .account(account)
                .isRejected(State.NORMAL)
                .deliveryRoom(room)
                .orderType(EntryOrderType.CONFIRM) // 주문자니까 궂이 입금 안해도 된다는 전제.
                .build();

        entryOrderTableRepository.save(leaderOrder);

    }
}
