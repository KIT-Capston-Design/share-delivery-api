package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRoomDTO {

    private Leader leader;

    private Long deliveryRoomId;

    private String content;
    private Long deliveryTip;
    private Long person;
    private Long limitPerson;
    private String storeLink;
    private String platformType;
    private String status;
    private LocalDateTime createdDateTime;

    private LocationDTO receivingLocation;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Leader{
        private String nickname;
        private Double mannerScore;

    }
}
