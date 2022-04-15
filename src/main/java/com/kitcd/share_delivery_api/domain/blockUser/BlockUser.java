package com.kitcd.share_delivery_api.domain.blockUser;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "BLOCK_USER")
public class BlockUser extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "BLOCK_ID", nullable = false)
   private Long blockId;

   @Column(name = "USER_ID", nullable = false)
   private User user;

   @Column(name = "BLOCKED_USER_ID", nullable = false)
   private User blockedUser;


}
