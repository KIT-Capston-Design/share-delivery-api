package com.kitcd.share_delivery_api.dto.post;

import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.placeshare.PlaceShareDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Long postId;
    private SimpleAccountDTO writer;
    private String content;
    private String category;
    private LocalDateTime createdDateTime;
    private Long likes;
    private Long viewCount;
    private Location coordinate;
    private PlaceShareDTO placeShare;
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class PostDetail{
        private Location coordinate;
    }
}
