package com.kitcd.share_delivery_api.domain.jpa.account;

import com.kitcd.share_delivery_api.dto.account.BankAccountDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Getter

@NoArgsConstructor
@AllArgsConstructor

@ToString

@Embeddable
public class BankAccount {

    @Column(name = "ACCOUNT_HOLDER", nullable = true)
    private String accountHolder;

    @Column(name = "BANK", nullable = true)
    @Enumerated(EnumType.STRING)
    private BankType bank;

    @Column(name = "ACCOUNT_NUMBER", nullable = true)
    private String accountNumber;

    public BankAccountDTO toDTO(){
        return BankAccountDTO.builder()
                .accountHolder(accountHolder)
                .bank(bank.toString())
                .accountNumber(accountNumber)
                .build();
    }
}
