package com.kitcd.share_delivery_api.service.impl;

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
    public List<OrderMenu> enrollMainMenu(List<OrderMenuRequestDTO> orderMenus, List<OptionMenuRequestDTO> optionMenus) {
        List<OrderMenu> mainMenus = orderMenus.stream().map(i -> OrderMenu.builder() // orderMenu 부모 먼저 작성
                .amount(i.getAmount())
                .menuName(i.getMenuName())
                .build()).collect(Collectors.toList());

        orderMenuRepository.saveAll(mainMenus);

        List<OrderMenu> optionMenuList = optionMenus.stream().map(i -> OrderMenu.builder() //옵션들 추가
                .menuName(i.getOptionName())
                .amount(i.getAmount())
                .parentMenu(mainMenus.stream().filter(j -> j.getMenuName().equals(i.getParent())).findAny().orElse(null))
                .build()).collect(Collectors.toList());

        orderMenuRepository.saveAll(optionMenuList);

        mainMenus.addAll(optionMenuList); // 두 개의 리스트 합치기
        return mainMenus;
    }
}
