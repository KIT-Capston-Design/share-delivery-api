package com.kitcd.share_delivery_api.security.exception;

import org.springframework.security.core.AuthenticationException;

public class NotSupportedAuthException extends AuthenticationException {
    public NotSupportedAuthException(String msg) {
        super(msg);
    }
}