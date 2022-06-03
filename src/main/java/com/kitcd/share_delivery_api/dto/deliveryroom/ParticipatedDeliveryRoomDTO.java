package com.kitcd.share_delivery_api.dto.deliveryroom;


import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipatedDeliveryRoomDTO {
    private Long roomId;
    private String leaderName;

    private String content;

    private Long peopleNumber;
    private Long limitPerson;

    private String storeName;

    private PlatformType platformType;
    private LocalDateTime createDateTime;

    private DeliveryRoomState status;

    private String receivingLocationDesc;
    private String storeCategory;

}
