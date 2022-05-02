package com.kitcd.share_delivery_api.dto.ordermenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenuRequestDTO {
    private String MenuName;
    private Long price;
    private Long amount;
}
