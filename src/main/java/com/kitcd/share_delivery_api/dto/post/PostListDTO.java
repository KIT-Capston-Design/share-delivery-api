package com.kitcd.share_delivery_api.dto.post;

import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListDTO {
    private Long postId;
    private SimpleAccountDTO writer;
    private String content;
    private String category;
    private LocalDateTime createdDateTime;
}
