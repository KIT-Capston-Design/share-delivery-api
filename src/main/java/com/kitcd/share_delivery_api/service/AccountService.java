package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;

public interface AccountService {
    Account saveAccount(Account account);
    Account findByPhoneNumber(String phoneNumber);
}
