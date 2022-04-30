package com.kitcd.share_delivery_api.domain.redis.auth.verificationsms;


import lombok.Getter;

@Getter
public enum VerificationType {

    SIGNUP("회원가입"),
    LOGIN("로그인"),
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
