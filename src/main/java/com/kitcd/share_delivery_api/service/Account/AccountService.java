package com.kitcd.share_delivery_api.service.Account;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;

public interface AccountService {
    Account saveAccount(Account account);
}
