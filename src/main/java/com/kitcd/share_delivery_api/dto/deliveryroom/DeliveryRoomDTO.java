package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRoomDTO {

    private SimpleAccountDTO leader;

    private Long deliveryRoomId;

    private String content;
    private Long deliveryTip;
    private Long person;
    private Long limitPerson;
    private String storeLink;
    private PlatformType platformType;
    private DeliveryRoomState status;
    private LocalDateTime createdDateTime;
    private LocationDTO receivingLocation;
}
