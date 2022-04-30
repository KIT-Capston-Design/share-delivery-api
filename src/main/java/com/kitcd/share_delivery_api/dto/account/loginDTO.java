package com.kitcd.share_delivery_api.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class loginDTO {
    private String phoneNumber;
    private String verificationCode;
    private String fcmToken;
}
