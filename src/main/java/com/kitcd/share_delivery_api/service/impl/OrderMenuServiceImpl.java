package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenuRepository;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.OrderMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderMenuServiceImpl implements OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    @Override
    public void enrollMenu(EntryOrder entryOrder, List<OrderMenuRequestDTO> orderMenus) {
        for (OrderMenuRequestDTO orderMenu : orderMenus) {
            OrderMenu parent = orderMenuRepository.save(orderMenu.mainToEntity(null, entryOrder));

            orderMenuRepository.saveAll(orderMenu.optionToEntity(entryOrder, parent));
        } //옵션들 저장.

    }
}
