package com.kitcd.share_delivery_api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.dto.placeshare.PlaceShareRequestDTO;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.GeometriesFactory;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WritePostRequestDTO {
    private Location coordinate;
    private String content;
    private String category;
    @JsonInclude(JsonInclude.Include.NON_NULL) //placeShare가 null이면 필드 안쓰도록.
    private PlaceShareRequestDTO placeShare;



    public Post toEntity(PostCategory postCategory, String address){
        return Post.builder()
                .content(content)
                .account(ContextHolder.getAccount())
                .address(address)
                .coordinate(coordinate)
                .likeCount(0L)
                .viewCount(0L)
                .postCategory(postCategory)
                .status(State.NORMAL)
                .pLocation(GeometriesFactory.createPoint(coordinate.getLatitude(), coordinate.getLongitude()))
                .build();
    }
}
