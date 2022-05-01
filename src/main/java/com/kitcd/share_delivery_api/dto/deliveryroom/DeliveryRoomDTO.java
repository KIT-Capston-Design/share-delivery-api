package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
public class DeliveryRoomDTO {

    private Leader leader;

    private String content;
    private String person;
    private String limitPerson;
    private String storeLink;
    private String platformType;
    private String status;
    private String createdDateTime;
    private String limitDateTime;

    private LocationDTO receivingLocation;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Leader{
        private String nickname;
        private Long mannerScore;

    }
}
