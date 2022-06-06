package com.kitcd.share_delivery_api.dto.commentlike;

import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentLikeDTO {
    private Long commentId;
    private Long likes;
    private Boolean isLiked;


    public static CommentLike toEntity(Comment comment){
        return CommentLike.builder()
                .comment(comment)
                .account(ContextHolder.getAccount())
                .build();
    }
}
