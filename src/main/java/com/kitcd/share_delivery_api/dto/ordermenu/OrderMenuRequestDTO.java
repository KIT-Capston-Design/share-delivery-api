package com.kitcd.share_delivery_api.dto.ordermenu;

import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenuRequestDTO {
    private String menuName;
    private Long menuPrice;
    private Long amount;
    private List<OrderMenuRequestDTO> optionList;

    public List<OrderMenu> optionToEntity(EntryOrder entryOrder, OrderMenu parent){
        return optionList.stream().map(i ->
                OrderMenu.builder()
                        .menuName(i.menuName)
                        .order(entryOrder)
                        .parentMenu(parent)
                        .price(i.menuPrice)
                        .quantity(i.amount)
                        .build()).collect(Collectors.toList());
    }

    public OrderMenu mainToEntity(OrderMenu parent, EntryOrder entryOrder) {
        return OrderMenu.builder()
                .price(this.menuPrice)
                .quantity(this.amount)
                .menuName(this.menuName)
                .parentMenu(parent)
                .order(entryOrder)
                .build();
    }
}
