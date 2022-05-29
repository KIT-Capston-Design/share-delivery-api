package com.kitcd.share_delivery_api.domain.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByPhoneNumber(String phoneNumber);
    Account findByEmail(String email);

    @Query("select a.bankAccount from Account a where a.accountId = :accountId")
    BankAccount getBankAccountById(Long accountId);

}
