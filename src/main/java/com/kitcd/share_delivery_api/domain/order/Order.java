package com.kitcd.share_delivery_api.domain.order;

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
@Table(name = "ORDER")
public class Order extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ORDER_ID")
   private Long orderId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "DELIVERY_ROOM_ID")
   private Long deliveryRoomId;

   @Column(name = "ORDER_TYPE")
   private String orderType;

   @Column(name = "IS_REJECTED")
   private String isRejected;


}
