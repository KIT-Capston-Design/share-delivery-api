package com.kitcd.share_delivery_api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WritePostRequestDTO {
    private Location coordinate;
    private String cotent;
    private String category;

    @JsonInclude(JsonInclude.Include.NON_NULL) //sharePlace가 null이면 필드 안쓰도록.
    private SharePlaceDTO sharePlace;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class SharePlaceDTO{
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


    public Post toEntity(){
        return Post.builder()
                .content(cotent)
                .account(ContextHolder.getAccount())
                .Address(new FindAddressWithLocation().coordToAddr(coordinate))
                .coordinate(coordinate)
                .likeCount(0L)
                .view_count(0L)
                .build();
    }
}
