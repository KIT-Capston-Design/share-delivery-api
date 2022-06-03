package com.kitcd.share_delivery_api.dto.deliveryroom;


import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinRequestDeliveryRoomDTO {

    @NotEmpty
    private List<OrderMenuRequestDTO> menuList;
}
