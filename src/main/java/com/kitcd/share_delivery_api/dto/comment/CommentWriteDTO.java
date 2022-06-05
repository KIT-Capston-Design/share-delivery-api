package com.kitcd.share_delivery_api.dto.comment;

import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentWriteDTO {
    private Long postId;
    private Long parentId;
    private String content;


    public Comment toEntity(Comment parent, Post post){
        return Comment.builder()
                .account(ContextHolder.getAccount())
                .content(content)
                .likeCount(0L)
                .parent(parent)
                .status(State.NORMAL)
                .post(post)
                .build();
    }
}
