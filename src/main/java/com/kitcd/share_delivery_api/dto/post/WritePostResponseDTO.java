package com.kitcd.share_delivery_api.dto.post;

import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WritePostResponseDTO {
    private Long postId;
    private PostDetail postDetail;
    private Writer writer;
    private String content;
    private String category;
    private LocalDateTime createdDateTime;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Writer{
        private Long accountId;
        private String nickName;
        private double mannerScore;
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
