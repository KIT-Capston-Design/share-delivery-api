package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderTableRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.service.EntryOrderService;
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

    @Override
    public EntryOrder enrollEntryOrder(List<OrderMenu> menus, Account account, DeliveryRoom room, EntryOrderType entryOrderType) {
        EntryOrder entryOrder = EntryOrder.builder()
                .orderMenus(menus)
                .account(account)
                .isRejected(State.NORMAL)
                .deliveryRoom(room)
                .orderType(entryOrderType)
                .build();

        entryOrderTableRepository.save(entryOrder);

        return entryOrder;
    }
}
