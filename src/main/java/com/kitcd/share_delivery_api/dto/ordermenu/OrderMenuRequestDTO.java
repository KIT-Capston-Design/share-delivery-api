package com.kitcd.share_delivery_api.dto.ordermenu;

import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderMenuRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Long quantity;

    private List<OrderMenuRequestDTO> optionList;

    public List<OrderMenu> optionToEntity(EntryOrder entryOrder, OrderMenu parent){
        if(optionList == null || optionList.isEmpty()) return null;
        return optionList.stream()
                .filter(Objects::nonNull)
                .map(i -> OrderMenu.builder()
                        .menuName(i.name)
                        .order(entryOrder)
                        .parentMenu(parent)
                        .price(i.price)
                        .quantity(i.quantity)
                        .build())
                .collect(Collectors.toList());
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
