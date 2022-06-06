package com.kitcd.share_delivery_api.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleAccountDTO {
    private Long accountId;
    private String nickname;
    private Double mannerScore;
    private BankAccountDTO bankAccount;
}
