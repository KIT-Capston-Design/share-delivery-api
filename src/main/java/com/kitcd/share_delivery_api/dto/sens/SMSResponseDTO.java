package com.kitcd.share_delivery_api.dto.sens;

import jdk.jfr.Timestamp;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SMSResponseDTO {
    private String requestId;
    private String statusCode;
    private String statusName;
    private LocalDateTime requestTime;
}
