package com.kitcd.share_delivery_api.security.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class AccountContext extends User {

    private final Account account;

    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getPhoneNumber(), "dummy", authorities);
        this.account = account;

    }

    public Account getAccount() {
        return account;
    }

}



