package com.kitcd.share_delivery_api.dto.receivinglocation;

import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingLocationDTO {
    private Long id;
    private String description;
    private Double lat;
    private Double lng;
    private Boolean isFavorite;
    private String address;

    public ReceivingLocationDTO(ReceivingLocation receivingLocation){
        id = receivingLocation.getReceivingLocationId();
        description = receivingLocation.getDescription();
        lat = receivingLocation.getCoordinate().getLatitude();
        lng = receivingLocation.getCoordinate().getLongitude();
        address = receivingLocation.getAddress();
    }

    public Coordinate createCoordinate() {
        return new Coordinate(lat, lng);
    }
}
