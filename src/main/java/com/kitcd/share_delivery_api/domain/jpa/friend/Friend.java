package com.kitcd.share_delivery_api.domain.jpa.friend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @ManyToOne
   @JoinColumn(name = "FRIEND_ACCOUNT_ID", nullable = false)
   private Account friendAccount;

   @Column(name = "STATUS", nullable = false)
   private State status;

}
