package com.kitcd.share_delivery_api.domain.redis.auth;


import lombok.Getter;

@Getter
public enum VerificationType {

    SIGNUP("회원가입"),
    LOGIN("로그인");

    private final String typeString;

    VerificationType(String typeString) {
        this.typeString = typeString;
    }

    @Override
    public String toString() {
        return typeString;
    }
}
