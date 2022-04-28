package com.kitcd.share_delivery_api.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginDTO {
    private String phoneNumber;
    private String password;
}
