package com.kitcd.share_delivery_api.dto.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginDTO {
    private String phoneNumber;
    private String password;
}
