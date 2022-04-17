package com.kitcd.share_delivery_api.domain.account;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@Getter

@NoArgsConstructor
@AllArgsConstructor

@ToString

@Embeddable
public class BankAccount {
    @Column(name = "BANK", nullable = true)
    private String bank;

    @Column(name = "ACCOUNT_NUMBER", nullable = true)
    private String accountNumber;
}
