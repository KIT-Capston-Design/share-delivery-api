package com.kitcd.share_delivery_api.domain.userFastDeliveryTag;

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
@Table(name = "USER_FAST_DELIVERY_TAG")
public class UserFastDeliveryTag extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FAST_DELIVERY_TAG_ID")
   private Long fastDeliveryTagId;

   @Column(name = "USER1_ID")
   private Long user1Id;

   @Column(name = "USER2_ID")
   private Long user2Id;


}
