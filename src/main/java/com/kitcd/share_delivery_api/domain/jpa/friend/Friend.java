package com.kitcd.share_delivery_api.domain.jpa.friend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "FRIEND")
public class Friend extends BaseTimeEntity {
   @JsonIgnore
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FRIEND_ID", nullable = false)
   private Long friendId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "FIRST_ACCOUNT_ID", nullable = false)
   private Account firstAccount;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "SECOND_ACCOUNT_ID", nullable = false)
   private Account secondAccount;

   @Column(name = "STATUS", nullable = false)
   private State status;

   public Account getFriendAccount(Long myAccountId){
      if(Objects.equals(myAccountId, firstAccount.getAccountId())) return secondAccount;
      else if(Objects.equals(myAccountId, secondAccount.getAccountId())) return firstAccount;
      throw new IllegalArgumentException("해당 Friend Entity는 내 Friend Entity가 아닙니다.");
   }
}
