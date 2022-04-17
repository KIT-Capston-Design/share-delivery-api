package com.kitcd.share_delivery_api.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByPhoneNumber(String phoneNumber);
}
