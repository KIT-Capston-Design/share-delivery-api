package com.kitcd.share_delivery_api.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountEmailAddDTO {
    private String email;
    private String verificationNum;
}
