package com.kitcd.share_delivery_api.dto.comment;

import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
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


    public Comment toEntity(Comment parent){
        return Comment.builder()
                .account(ContextHolder.getAccount())
                .content(content)
                .likeCount(0L)
                .parent(parent)
                .build();
    }
}
