package com.kitcd.share_delivery_api.dto.receivinglocation;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.utils.geometry.Location;
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
        lat = receivingLocation.getLocation().getLatitude();
        lng = receivingLocation.getLocation().getLongitude();
        address = receivingLocation.getAddress();
    }

    public Location createCoordinate() {
        return new Location(lat, lng);
    }
    public ReceivingLocation toEntity(Account account){
        ReceivingLocation receivingLocation = ReceivingLocation.builder()
                .account(account)
                .location(this.createCoordinate())
                .description(this.getDescription())
                .address(this.getAddress())
                .isFavorite(this.getIsFavorite())
                .build();
        return receivingLocation;
    }
}
