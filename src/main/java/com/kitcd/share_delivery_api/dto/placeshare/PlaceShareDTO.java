package com.kitcd.share_delivery_api.dto.placeshare;

import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlaceShareDTO {
    private Long placeShareId;
    private Double latitude;
    private Double longitude;
    private String description;


    public static PlaceShareDTO parseDTO(PlaceShare placeShare){
        PlaceShareDTO dto = new PlaceShareDTO();
        dto.description = placeShare.getContent();
        dto.latitude = placeShare.getCoordinate().getLatitude();
        dto.longitude = placeShare.getCoordinate().getLongitude();
        dto.placeShareId = placeShare.getPlaceShareId();
        return dto;
    }
}
