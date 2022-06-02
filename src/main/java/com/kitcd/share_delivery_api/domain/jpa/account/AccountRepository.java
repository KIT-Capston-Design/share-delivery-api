package com.kitcd.share_delivery_api.domain.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a left join fetch a.profileImage where a.phoneNumber = :phoneNumber")
    Account findByPhoneNumber(String phoneNumber);
    Account findByEmail(String email);

    @Query("select a.bankAccount from Account a where a.accountId = :accountId")
    BankAccount getBankAccountById(Long accountId);

    Account findByAccountId(Long accountId);
}
