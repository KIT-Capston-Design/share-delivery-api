package com.kitcd.share_delivery_api.dto.comment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUpdateDTO {
    private String content;
}
