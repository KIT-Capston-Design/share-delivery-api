package com.kitcd.share_delivery_api.dto.remittance;


import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemittancePatchDTO {
    @NotNull
    private Long remittanceId;
    @NotNull
    private Boolean isRemitted;
}
