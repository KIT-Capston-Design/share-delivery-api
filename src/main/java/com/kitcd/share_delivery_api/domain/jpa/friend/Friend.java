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

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private State status;

   public Account getFriendAccount(Long myAccountId){
      if(Objects.equals(myAccountId, firstAccount.getAccountId())) return secondAccount;
      else if(Objects.equals(myAccountId, secondAccount.getAccountId())) return firstAccount;
      throw new IllegalArgumentException("해당 Friend Entity는 내 Friend Entity가 아닙니다.");
   }

   public State accept(Long requestedPersonId){

      if(!getFirstAccount().getAccountId().equals(requestedPersonId))
         throw new IllegalStateException("본인이 신청한 친구 요청을 본인이 수락할 수 없습니다.");

      if(!status.equals(State.PENDING))
         throw new IllegalStateException("친구 요청을 승낙할 수 있는 상태가 아닙니다.");

      return status = State.ACCEPTED;
   }

   public State reject(Long requestedPersonId) {

      if(!getFirstAccount().getAccountId().equals(requestedPersonId))
         throw new IllegalStateException("본인이 신청한 친구 요청을 본인이 거절할 수 없습니다.");

      if(!status.equals(State.PENDING))
         throw new IllegalStateException("친구 요청을 거절할 수 있는 상태가 아닙니다.");

      return status = State.REJECTED;
   }

   public State cancel(Long personId) {

      //상대방의 요청을 거절할 수는 있어도 취소할 수는 없음.
      if(!getSecondAccount().getAccountId().equals(personId))
         throw new IllegalStateException("잘못된 연산 : 상대방의 요청을 거절할 수는 있어도 취소할 수는 없습니다.");

      if(!status.equals(State.PENDING))
         throw new IllegalStateException("친구 요청을 취소할 수 있는 상태가 아닙니다.");

      return status = State.CANCELLED;
   }
}
