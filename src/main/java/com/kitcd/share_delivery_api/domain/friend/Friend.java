package com.kitcd.share_delivery_api.domain.friend;

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
@Table(name = "FRIEND")
public class Friend extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FRIEND_ID")
   private Long friendId;

   @Column(name = "USER1_ID")
   private Long user1Id;

   @Column(name = "USER2_ID")
   private Long user2Id;

   @Column(name = "STATUS")
   private String status;


}
