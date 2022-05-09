package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.dto.ordermenu.OptionMenuRequestDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRoomEnrollRequestDTO {
    private String content;
    private Long receivingLocationId;
    private Long limitPerson;
    private StoreCategory storeCategory;
    private Long deliveryTip;
    private List<OrderMenuRequestDTO> menuList;
    private List<OptionMenuRequestDTO> optionList;
    private String shareStoreLink;
    private PlatformType linkPlatformType;
}
