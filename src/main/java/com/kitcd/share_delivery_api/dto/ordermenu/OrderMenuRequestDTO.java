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
    private String name;
    private Long price;
    private Long quantity;
    private List<OrderMenuRequestDTO> optionList;

    public List<OrderMenu> optionToEntity(EntryOrder entryOrder, OrderMenu parent){
        return optionList.stream().map(i ->
                OrderMenu.builder()
                        .menuName(i.name)
                        .order(entryOrder)
                        .parentMenu(parent)
                        .price(i.price)
                        .quantity(i.quantity)
                        .build()).collect(Collectors.toList());
    }

    public OrderMenu mainToEntity(OrderMenu parent, EntryOrder entryOrder) {
        return OrderMenu.builder()
                .price(this.price)
                .quantity(this.quantity)
                .menuName(this.name)
                .parentMenu(parent)
                .order(entryOrder)
                .build();
    }
}
