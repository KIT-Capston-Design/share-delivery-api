package com.kitcd.share_delivery_api.domain.userBlock;

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
@Table(name = "USER_BLOCK")
public class UserBlock extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "BLOCK_ID", nullable = false)
   private Long blockId;

   @ManyToOne
   @JoinColumn(name = "USER_ID", nullable = false)
   private User user;

   @ManyToOne
   @JoinColumn(name = "BLOCKED_USER_ID", nullable = false)
   private User blockedUser;


}
