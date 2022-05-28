package com.kitcd.share_delivery_api.domain.jpa.account;

import lombok.Getter;

public enum BankType {
    KAKAO("카카오뱅크");

    private final String bankName;

    BankType(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return bankName;
    }
}
