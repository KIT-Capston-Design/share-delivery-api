package com.kitcd.share_delivery_api.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JsonWebTokenDTO {
    String accessToken;
    String refreshToken;
}
