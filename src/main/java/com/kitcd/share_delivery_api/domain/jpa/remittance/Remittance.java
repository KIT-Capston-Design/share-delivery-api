package com.kitcd.share_delivery_api.domain.jpa.remittance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "REMITTANCE")
public class Remittance extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "REMITTANCE_ID", nullable = false)
   private Long remittanceId;

   @ManyToOne
   @JoinColumn(name = "PAYMENT_ID", nullable = false)
   private Payment payment;

   @ManyToOne
   @JoinColumn(name = "REMITTER_ID", nullable = false)
   private Account remitter;

   @ManyToOne
   @JoinColumn(name = "RECIPIENT_ID", nullable = false)
   private Account recipient;

   @Column(name = "AMOUNT", nullable = false)
   private Long amount;

   @Enumerated(EnumType.STRING)
   @Column(name = "IS_REMITTED", nullable = false)
   private State isRemitted;

}
