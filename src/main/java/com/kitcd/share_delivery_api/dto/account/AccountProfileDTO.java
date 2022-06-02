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
public class AccountProfileDTO {
    private Long accountId ;
    private String nickname ;
    private LocalDateTime createdDate ;
    private LocalDateTime modifiedDate ;
    private String profileImageUrl ;
    private Double mannerScore;

}
