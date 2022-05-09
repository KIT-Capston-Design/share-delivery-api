package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
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
    private ReceivingLocationDTO receivingLocation;
    private Long limitPerson;
    private StoreCategory storeCategory;
    private Long deliveryTip;
    private List<OrderMenuRequestDTO> menuList;
    private List<OptionMenuRequestDTO> optionList;
    private String shareStoreLink;
    private PlatformType linkPlatformType;

    public DeliveryRoom toEntity(DeliveryRoomEnrollRequestDTO dto, Account account){ //리더 정보와
        DeliveryRoom room = DeliveryRoom.builder()
                .content(dto.getContent())
                .receivingLocation(receivingLocation.toEntity(receivingLocation, account)) //저장한다음 연관관계 매핑
                .leader(account)
                .limitPerson(dto.getLimitPerson())
                .linkPlatformType(dto.getLinkPlatformType())
                .status(DeliveryRoomState.WAITING)
                .storeCategory(dto.getStoreCategory())
                .build();
        return room;
    }
}
