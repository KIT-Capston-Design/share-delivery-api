package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderTableRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.EntryOrderService;
import com.kitcd.share_delivery_api.service.OrderMenuService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EntryOrderServiceImpl implements EntryOrderService {

    private final EntryOrderTableRepository entryOrderTableRepository;
    private final OrderMenuService orderMenuService;

    @Override
    public EntryOrder enrollEntryOrder(DeliveryRoom room, List<OrderMenuRequestDTO> menuList, EntryOrderType entryOrderType) {

        EntryOrder entryOrder = entryOrderTableRepository.save(EntryOrder.builder()
                .account(ContextHolder.getAccount())
                .isRejected(State.NORMAL)
                .deliveryRoom(room)
                .orderType(entryOrderType)
                .build());

        orderMenuService.enrollMenu(entryOrder, menuList);

        return entryOrder;
    }
}
