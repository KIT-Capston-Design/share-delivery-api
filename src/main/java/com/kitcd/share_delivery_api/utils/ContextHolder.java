package com.kitcd.share_delivery_api.utils;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.security.service.AccountContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextHolder {

    public static Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
