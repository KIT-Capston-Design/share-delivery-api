package com.kitcd.share_delivery_api.dto.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleAccountDTO {
    private Long accountId;
    private String nickname;
    private double mannerScore;
}
