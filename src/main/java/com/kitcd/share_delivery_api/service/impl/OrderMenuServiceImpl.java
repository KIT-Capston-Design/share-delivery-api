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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderMenuServiceImpl implements OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    @Override
    public void enrollMenu(EntryOrder entryOrder, List<OrderMenuRequestDTO> orderMenus) {

        orderMenus = orderMenus.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if(orderMenus.isEmpty()) throw new IllegalArgumentException("메뉴가 비어있습니다.");

        for (OrderMenuRequestDTO orderMenu : orderMenus) {
            OrderMenu parent = orderMenuRepository.save(orderMenu.mainToEntity(null, entryOrder));

            //옵션들 저장.
            orderMenuRepository.saveAll(orderMenu.optionToEntity(entryOrder, parent));
        }

    }
}
