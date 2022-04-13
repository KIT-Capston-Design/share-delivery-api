package com.kitcd.share_delivery_api.domain.remittance;

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
@Table(name = "REMITTANCE")
public class Remittance extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "REMITTANCE_ID")
   private Long remittanceId;

   @Column(name = "FAST_DELIVERY_TAG_ID")
   private Long fastDeliveryTagId;

   @Column(name = "PAYMENT_ID")
   private Long paymentId;

   @Column(name = "REMITTER_ID")
   private Long remitterId;

   @Column(name = "RECIPIENT_ID")
   private Long recipientId;

   @Column(name = "AMOUNT")
   private Long amount;

   @Column(name = "IS_REMITTED")
   private String isRemitted;


}
