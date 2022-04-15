package com.kitcd.share_delivery_api.domain.order;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
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
@Table(name = "ORDER")
public class Order extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ORDER_ID", nullable = false)
   private Long orderId;

   @Column(name = "USER_ID", nullable = false)
   private User user;

   @Column(name = "DELIVERY_ROOM_ID", nullable = false)
   private DeliveryRoom deliveryRoom;

   @Enumerated(EnumType.STRING)
   @Column(name = "ORDER_TYPE", nullable = false)
   private OrderType orderType;

   @Enumerated(EnumType.STRING)
   @Column(name = "IS_REJECTED", nullable = false)
   private State isRejected;


}
