package com.kitcd.share_delivery_api.dto.account;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.RoleType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRegDTO {

    private String phoneNumber;

    private final RoleType role = RoleType.ROLE_USER;

    public Account toEntity() {
        return Account.builder()
                .phoneNumber(this.phoneNumber)
                .role(this.role)
                .build();
    }
}
