package com.kitcd.share_delivery_api.domain.payment;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
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
   @Column(name = "PAYMENT_ID", nullable = false)
   private Long paymentId;

   @Column(name = "DELIVERY_ROOM_ID", nullable = false)
   private DeliveryRoom deliveryRoom;

   @Column(name = "DELIVERY_TIME", nullable = false)
   private Long deliveryTime;

   @Column(name = "DELIVERY_FEE", nullable = false)
   private Long deliveryFee;


}
