package com.kitcd.share_delivery_api.dto.receivinglocation;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.utils.geometry.GeometriesFactory;
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
        lat = receivingLocation.getPLocation().getY();
        lng = receivingLocation.getPLocation().getX();
        address = receivingLocation.getAddress();
    }

    public Location getLocation() {
        return new Location(lat, lng);
    }

    public ReceivingLocation toEntity(Account account){

        return ReceivingLocation.builder()
                .account(account)
                .pLocation(GeometriesFactory.createPoint(lat, lng))
                .description(description)
                .address(address)
                .isFavorite(isFavorite)
                .build();
    }
}
