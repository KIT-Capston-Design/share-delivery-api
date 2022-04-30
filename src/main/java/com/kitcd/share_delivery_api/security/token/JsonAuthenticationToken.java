package com.kitcd.share_delivery_api.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JsonAuthenticationToken extends AbstractAuthenticationToken {
//    private static final long serialVersionUID = 520L;
    private final Object principal;
    private Object credentials;
    private String fcmToken;


    // 인증 이전 입력 정보 담는
    public JsonAuthenticationToken(Object principal, Object credentials, String fcmToken) {
            super(null);
            this.principal = principal;
            this.credentials = credentials;
            this.fcmToken = fcmToken;
        this.setAuthenticated(false);
    }


    //인증 이후 결과 담는 (권한정보 추가)
    public JsonAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String fcmToken) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.fcmToken = fcmToken;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
