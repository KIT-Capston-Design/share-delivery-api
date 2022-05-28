package com.kitcd.share_delivery_api.dto.sens;

import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;

import java.time.LocalDateTime;

import lombok.*;

@Builder
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

    public static SMSResponseDTO dummyDTO(String phoneNumber){
        return SMSResponseDTO.builder()
                .requestId("DUMMY ID")
                .statusCode(202)
                .statusName("success")
                .requestTime(LocalDateTime.now())
                .phoneNumber(phoneNumber)
                .verificationType(VerificationType.LOGIN)
                .build();
    }
}
