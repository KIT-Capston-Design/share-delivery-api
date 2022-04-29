package com.kitcd.share_delivery_api.dto.sens;

import com.kitcd.share_delivery_api.domain.redis.auth.VerificationType;
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
    private Integer statusCode;
    private String statusName;
    private LocalDateTime requestTime;
    private String phoneNumber;
    private VerificationType verificationType;
}
