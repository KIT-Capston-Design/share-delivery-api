package com.kitcd.share_delivery_api.controller.common;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.security.service.AccountContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentLoggedInSession {

    public static Account getAccount() {
        AccountContext userData = (AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account user = userData.getAccount();
        return user;
    }
}
