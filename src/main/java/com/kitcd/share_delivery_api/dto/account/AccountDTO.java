package com.kitcd.share_delivery_api.dto.account;


import com.kitcd.share_delivery_api.domain.jpa.account.RoleType;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private LocalDateTime createdDate ;
    private LocalDateTime modifiedDate ;
    private Long accountId ;
    private String phoneNumber ;
    private String nickname ;
    private String profileImageUrl ;
    private String email ;
    private State status ;
    private RoleType role ;
    private BankAccountDTO bankAccount ;

}
