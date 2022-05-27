package com.kitcd.share_delivery_api.security.dto;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JWTDTO {
    Account account;
    String accessToken;
    String refreshToken;
}
