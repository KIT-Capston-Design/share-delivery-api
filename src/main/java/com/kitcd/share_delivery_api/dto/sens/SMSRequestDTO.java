package com.kitcd.share_delivery_api.dto.sens;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor

@Setter
@Getter
public class SMSRequestDTO{
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<MessageDTO> messages;
}