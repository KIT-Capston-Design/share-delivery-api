package com.kitcd.share_delivery_api.dto.Account;

import com.kitcd.share_delivery_api.domain.account.Account;
import com.kitcd.share_delivery_api.domain.account.RoleType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRegDTO {

    private String phoneNumber;

    private String password;

    private final RoleType role = RoleType.ROLE_USER;

    public Account toEntity() {
        return Account.builder()
                .phoneNumber(this.phoneNumber)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
