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
}
