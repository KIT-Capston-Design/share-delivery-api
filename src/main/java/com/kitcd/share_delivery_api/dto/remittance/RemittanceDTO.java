package com.kitcd.share_delivery_api.dto.remittance;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemittanceDTO {
    private Long remittanceId;
    private Long accountId;
    private String nickname;
    private Long amount;
    private State isRemitted;
}
