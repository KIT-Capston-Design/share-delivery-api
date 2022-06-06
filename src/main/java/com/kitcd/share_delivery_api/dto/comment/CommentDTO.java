package com.kitcd.share_delivery_api.dto.comment;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private SimpleAccountDTO writer;
    private Long id;
    private String content;
    private Long likes;
    private Boolean isLiked;
    private LocalDateTime createdDateTime;
    private Long parentId;
    private State state;
}
