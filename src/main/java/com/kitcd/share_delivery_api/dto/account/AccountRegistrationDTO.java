package com.kitcd.share_delivery_api.dto.account;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.RoleType;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class AccountRegistrationDTO {

    @Pattern(regexp = "^010[\\d]{8}")
    private String phoneNumber;
    @NotBlank
    private String verificationCode;

    private String nickname;

    public Account toEntity() {
        return Account.builder()
                .phoneNumber(this.phoneNumber)
                .nickname(nickname)
                .status(State.NORMAL)
                .role(RoleType.ROLE_USER)
                .build();
    }
}
