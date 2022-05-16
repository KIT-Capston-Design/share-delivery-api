package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import lombok.*;


import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRoomEnrollRequestDTO {
    private String content;
    @NotBlank
    private Long receivingLocationId;
    private Long limitPerson;
    private String storeCategory;
    private Long deliveryTip;
    private List<OrderMenuRequestDTO> menuList;
    private ShareStoreDTO shareStore;

    public DeliveryRoom toEntity(Account leader, ReceivingLocation location, StoreCategory storeCategory){
        return DeliveryRoom.builder()
                .content(this.getContent())
                .receivingLocation(location)
                .leader(leader)
                .limitPerson(this.getLimitPerson())
                .linkPlatformType(shareStore.type)
                .status(DeliveryRoomState.OPEN)
                .storeName(shareStore.name)
                .storeLink(shareStore.link)
                .status(DeliveryRoomState.OPEN)
                .estimatedDeliveryTip(deliveryTip)
                .storeCategory(storeCategory)
                .peopleNumber(1L)
                .build();
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    @Builder
    public static class ShareStoreDTO{
        private String name;
        private String link;
        private PlatformType type;
    }
}
