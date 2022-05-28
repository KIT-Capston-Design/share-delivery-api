package com.kitcd.share_delivery_api.dto.account;

import com.kitcd.share_delivery_api.domain.jpa.account.BankType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDTO {

    private String accountHolder;
    private String bank;
    private String accountNumber;
}
