package com.kitcd.share_delivery_api.dto.ordermenu;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MenuResDTO {
    private Long orderMenuId;
    private String menuName;
    private Long quantity;
    private Long price;
    private List<MenuResDTO> optionMenus;
}
