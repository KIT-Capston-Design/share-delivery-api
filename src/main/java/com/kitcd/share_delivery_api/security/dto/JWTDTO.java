package com.kitcd.share_delivery_api.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JWTDTO {
    String accessToken;
    String refreshToken;
}
