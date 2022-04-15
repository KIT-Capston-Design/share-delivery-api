package com.kitcd.share_delivery_api.domain.friend;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.user.User;
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
@Table(name = "FRIEND")
public class Friend extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FRIEND_ID", nullable = false)
   private Long friendId;

   @Column(name = "USER_A_ID", nullable = false)
   private User userA;

   @Column(name = "USER_B_ID", nullable = false)
   private User userB;

   @Column(name = "STATUS", nullable = false)
   private State status;

}
