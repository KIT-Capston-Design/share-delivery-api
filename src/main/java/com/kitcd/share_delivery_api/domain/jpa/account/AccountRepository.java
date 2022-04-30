package com.kitcd.share_delivery_api.domain.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByPhoneNumber(String phoneNumber);
    Account findByEmail(String email);
}
