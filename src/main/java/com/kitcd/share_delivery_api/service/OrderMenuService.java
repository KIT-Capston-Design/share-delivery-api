package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;

import java.util.List;

public interface OrderMenuService {
    void enrollMenu(EntryOrder entryOrder, List<OrderMenuRequestDTO> orderMenus);

}
