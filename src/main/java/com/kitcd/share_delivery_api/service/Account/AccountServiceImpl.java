package com.kitcd.share_delivery_api.service.Account;

import com.kitcd.share_delivery_api.domain.account.Account;
import com.kitcd.share_delivery_api.domain.account.AccountRepository;
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
}
