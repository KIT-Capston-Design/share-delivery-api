package com.kitcd.share_delivery_api.dto.sens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageDTO {
    private String to;
    private String content;
}
