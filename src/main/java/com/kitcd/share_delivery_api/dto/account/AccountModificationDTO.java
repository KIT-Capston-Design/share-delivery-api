package com.kitcd.share_delivery_api.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountModificationDTO {
    @Email
    private String email;

    private String nickname;

}
