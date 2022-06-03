package com.kitcd.share_delivery_api.dto.receivinglocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.utils.geometry.GeometriesFactory;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceivingLocationDTO {
    private Long id;
    private String description;
    private Double latitude;
    private Double longitude;
    private Boolean isFavorite;
    private String address;

    public ReceivingLocationDTO(ReceivingLocation receivingLocation){
        id = receivingLocation.getReceivingLocationId();
        description = receivingLocation.getDescription();
        latitude = receivingLocation.getPLocation().getY();
        longitude = receivingLocation.getPLocation().getX();
    }

    public ReceivingLocation toEntity(Account account){

        return ReceivingLocation.builder()
                .account(account)
                .pLocation(GeometriesFactory.createPoint(latitude, longitude))
                .locationRef(Location.builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .build())
                .description(description)
                .address(address)
                .isFavorite(false)
                .build();
    }
}
