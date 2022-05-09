package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderTableRepository;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenuRepository;
import com.kitcd.share_delivery_api.dto.ordermenu.OptionMenuRequestDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.OrderMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderMenuServiceImpl implements OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    @Override
    public List<OrderMenu> enrollMenu(EntryOrder entryOrder, List<OrderMenuRequestDTO> orderMenus) {

        List<OrderMenu> mainMenus = orderMenus.stream().map(i -> OrderMenu.builder() // orderMenu 부모 먼저 작성
                .amount(i.getAmount())
                .menuName(i.getMenuName())
                .order(entryOrder)
                .build()).collect(Collectors.toList());

        orderMenuRepository.saveAll(mainMenus);

        return mainMenus;
    }
}
