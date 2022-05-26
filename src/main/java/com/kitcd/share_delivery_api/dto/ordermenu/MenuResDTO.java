package com.kitcd.share_delivery_api.dto.ordermenu;

import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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

    public static MenuResDTO parseMenuResDTO(OrderMenu orderMenu){
        if(orderMenu == null){
            return null;
        }
        return MenuResDTO.builder()
                .menuName(orderMenu.getMenuName())
                .orderMenuId(orderMenu.getOrderMenuId())
                .price(orderMenu.getPrice())
                .quantity(orderMenu.getQuantity())
                .optionMenus(orderMenu.getChildMenus().stream().map(i -> MenuResDTO.parseMenuResDTO(i)).collect(Collectors.toList()))
                .build();
    }

    public static List<MenuResDTO> parseEntityListToDTO(List<OrderMenu> orderMenus){
        return orderMenus.stream().map(i -> MenuResDTO.parseMenuResDTO(i)).collect(Collectors.toList());
    }
}
