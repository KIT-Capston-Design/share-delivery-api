package com.kitcd.share_delivery_api.domain.jpa.postalarm;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "POST_ALARM")
public class PostAlarm extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_ALARM_ID", nullable = false)
   private Long postAlarmId;

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @ManyToOne
   @JoinColumn(name = "POST_ID", nullable = false)
   private Post post;


}
