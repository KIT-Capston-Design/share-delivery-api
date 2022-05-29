package com.kitcd.share_delivery_api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.dto.placeshare.PlaceShareRequestDTO;
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

    private PostDetail postDetail;
    private String cotent;
    private String category;
    @JsonInclude(JsonInclude.Include.NON_NULL) //sharePlace가 null이면 필드 안쓰도록.
    private PlaceShareRequestDTO sharePlace;


    public Post toEntity(PostCategory postCategory){
        return Post.builder()
                .content(cotent)
                .account(ContextHolder.getAccount())
                .Address(new FindAddressWithLocation().coordToAddr(postDetail.coordinate))
                .coordinate(postDetail.coordinate)
                .likeCount(0L)
                .viewCount(0L)
                .postCategory(postCategory)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class PostDetail{
        private Location coordinate;
    }
}
