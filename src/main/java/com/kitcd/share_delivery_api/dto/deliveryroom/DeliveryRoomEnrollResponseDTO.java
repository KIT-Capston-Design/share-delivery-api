package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;

import com.kitcd.share_delivery_api.dto.ordermenu.MenuResDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryRoomEnrollResponseDTO {
    private Long roomId;
    private String content;
    @NotBlank
    private Long receivingLocationId;
    private Long limitPerson;
    private String storeCategory;
    private Long deliveryTip;
    private List<MenuResDTO> menuList;
    private ShareStoreDTO shareStore;

    public DeliveryRoomEnrollResponseDTO parseDTO(List<OrderMenu> orderMenus, DeliveryRoom room){
        return DeliveryRoomEnrollResponseDTO.builder()
                .roomId(room.getDeliveryRoomId())
                .deliveryTip(room.getEstimatedDeliveryTip())
                .content(room.getContent())
                .receivingLocationId(room.getReceivingLocation().getReceivingLocationId())
                .limitPerson(room.getLimitPerson())
                .storeCategory(room.getStoreCategory().getCategoryName())
                .menuList(MenuResDTO.parseEntityListToDTO(orderMenus))
                .shareStore(ShareStoreDTO.builder().name(room.getStoreName())
                        .link(room.getStoreLink())
                        .type(room.getLinkPlatformType())
                        .build())
                .build();
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    @Builder
    private static class ShareStoreDTO{
        private String name;
        private String link;
        private PlatformType type;
    }
}
