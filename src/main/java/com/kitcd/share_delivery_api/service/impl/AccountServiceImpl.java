package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account saveAccount(Account account) {
        account.encodePassword(this.passwordEncoder);
        return this.accountRepository.save(account);
    }

    @Override
    public Account findByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

}
