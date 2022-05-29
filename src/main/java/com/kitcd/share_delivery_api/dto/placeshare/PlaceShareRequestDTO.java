package com.kitcd.share_delivery_api.dto.placeshare;

import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaceShareRequestDTO {
    private Double latitude;
    private Double longitude;
    private String description;

    public PlaceShare toEntity(Post post){
        Location location = new Location(latitude, longitude);
        return PlaceShare.builder()
                .content(description)
                .address(new FindAddressWithLocation().coordToAddr(location))
                .coordinate(location)
                .build();
    }
}