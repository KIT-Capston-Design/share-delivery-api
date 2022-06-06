package com.kitcd.share_delivery_api.domain.jpa.account;

import lombok.Getter;

public enum BankType {
    KAKAO("카카오뱅크"),
    NONGHYUP("농협"),
    KOOKMIN("국민"),
    SHINHAN("신한"),
    WOORI("우리"),
    IBK("기업"),
    HANA("하나"),
    KFCC("새마을금고"),
    EPOST("우체국"),
    SCB("SC제일"),
    DGB("대구"),
    BSB("부산"),
    KNB("경남"),
    KJB("광주"),
    SHB("신협"),
    SUHYUP("수협"),
    KDB("산업"),
    JBB("전북"),
    JJB("제주"),
    CITYB("씨티"),
    KBANK("케이뱅크");

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
