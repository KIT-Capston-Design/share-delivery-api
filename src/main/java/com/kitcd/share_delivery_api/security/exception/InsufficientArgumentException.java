package com.kitcd.share_delivery_api.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InsufficientArgumentException extends AuthenticationException {
    public InsufficientArgumentException(String msg) {
        super(msg);
    }
}