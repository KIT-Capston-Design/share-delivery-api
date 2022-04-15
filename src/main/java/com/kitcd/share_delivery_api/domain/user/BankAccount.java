package com.kitcd.share_delivery_api.domain.user;

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
    @Column(name = "BANK", nullable = false)
    private String bank;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;
}
