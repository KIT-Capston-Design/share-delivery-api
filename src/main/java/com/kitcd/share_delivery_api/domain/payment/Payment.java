package com.kitcd.share_delivery_api.domain.payment;

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
@Table(name = "PAYMENT")
public class Payment extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PAYMENT_ID")
   private Long paymentId;

   @Column(name = "DELIVERY_ROOM_ID")
   private Long deliveryRoomId;

   @Column(name = "DELIVERY_TIME")
   private Long deliveryTime;

   @Column(name = "DELIVERY_FEE")
   private Long deliveryFee;


}
