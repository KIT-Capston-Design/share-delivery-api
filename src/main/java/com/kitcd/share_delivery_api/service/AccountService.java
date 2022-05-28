package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;

public interface AccountService {
    Account signUp(AccountRegistrationDTO dto);
    Account findByPhoneNumber(String phoneNumber);

    BankAccount getBankAccount(Long accountId);

    Account saveMyBankAccount(BankAccount toEntity);
}
