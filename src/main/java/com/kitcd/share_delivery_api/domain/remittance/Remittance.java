package com.kitcd.share_delivery_api.domain.remittance;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.payment.Payment;
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
@Table(name = "REMITTANCE")
public class Remittance extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "REMITTANCE_ID", nullable = false)
   private Long remittanceId;

   @Column(name = "FAST_DELIVERY_TAG_ID", nullable = false)
   private Long fastDeliveryTagId;

   @Column(name = "PAYMENT_ID", nullable = false)
   private Payment payment;

   @Column(name = "REMITTER_ID", nullable = false)
   private User remitter;

   @Column(name = "RECIPIENT_ID", nullable = false)
   private User recipient;

   @Column(name = "AMOUNT", nullable = false)
   private Long amount;

   @Enumerated
   @Column(name = "IS_REMITTED", nullable = false)
   private State isRemitted;

}
