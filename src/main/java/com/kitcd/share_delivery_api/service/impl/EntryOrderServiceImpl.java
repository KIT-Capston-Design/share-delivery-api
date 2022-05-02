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
    public EntryOrder enrollEntryOrder(List<OrderMenu> menus, Account account, DeliveryRoom room) {
        EntryOrder entryOrder = EntryOrder.builder()
                .orderMenus(menus)
                .account(account)
                .isRejected(State.NORMAL)
                .deliveryRoom(room)
                .orderType(EntryOrderType.CONFIRM) // 주문자니까 궂이 입금 안해도 된다는 전제.
                .build();

        entryOrderTableRepository.save(entryOrder);

        return entryOrder;
    }
}
