package com.kitcd.share_delivery_api.dto.entryorder;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.dto.ordermenu.MenuResDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class OrderResDTO {
    private Long entryOrderId;
    private Long accountId;
    private String nickName;
    private EntryOrderType type;
    private State status;
    private List<MenuResDTO> menus;
    private LocalDateTime createdDateTime;
}
