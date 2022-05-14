package com.kitcd.share_delivery_api.dto.entryorder;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.dto.ordermenu.MenuDTO;

import java.time.LocalDateTime;
import java.util.List;


public class OrderResDTO {
    private Long entryOrderId;
    private Long accountId;
    private EntryOrderType type;
    private State status;
    private List<MenuDTO> menus;
    private LocalDateTime createdDateTime;
}
