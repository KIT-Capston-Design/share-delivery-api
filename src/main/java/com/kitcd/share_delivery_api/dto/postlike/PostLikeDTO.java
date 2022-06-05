package com.kitcd.share_delivery_api.dto.postlike;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostLikeDTO {
    private Long postId;
    private Long likes;
    private Boolean isLiked;

    public static PostLike toEntity(Post post){
        return PostLike.builder()
                .post(post)
                .account(ContextHolder.getAccount())
                .build();
    }
}
