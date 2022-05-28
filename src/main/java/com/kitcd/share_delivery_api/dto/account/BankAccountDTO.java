package com.kitcd.share_delivery_api.dto.account;

import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.domain.jpa.account.BankType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDTO {

    @NotBlank
    private String accountHolder;
    @NotBlank
    private String bank;
    @NotBlank
    private String accountNumber;

    public BankAccount toEntity(){
        return BankAccount.builder()
                .accountHolder(accountHolder)
                .bank(BankType.valueOfBankName(bank))
                .accountNumber(accountNumber)
                .build();
    }
}
