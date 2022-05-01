package com.kitcd.share_delivery_api.domain.redis.auth.verificationemail;


import lombok.Getter;

@Getter
public enum VerificationType {

    VEREMAIL("이메일 인증"),
    CHANGEID("아이디 변경");

    private final String typeString;

    VerificationType(String typeString) {
        this.typeString = typeString;
    }

    @Override
    public String toString() {
        return typeString;
    }
}
