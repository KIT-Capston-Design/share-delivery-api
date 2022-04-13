package com.kitcd.share_delivery_api.domain.blockedFriend;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "BLOCKED_FRIEND")
public class BlockedFriend extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "BLOCKED_FRIEND")
   private Long blockedFriend;

   @Column(name = "USER1_ID")
   private Long user1Id;

   @Column(name = "USER2_ID")
   private Long user2Id;


}
