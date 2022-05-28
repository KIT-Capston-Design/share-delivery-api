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


    public static BankType valueOfBankName(String bankName) {
        for (BankType value : values()) {
            if (value.bankName.equals(bankName)) {
                return value;
            }
        }
        //일치하는 은행 명이 없을 경우
        throw new IllegalArgumentException(bankName + " is unknown bank name");
    }
}
