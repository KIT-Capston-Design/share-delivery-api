package com.kitcd.share_delivery_api.domain.jpa.paymentDiscount;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "PAYMENT_DISCOUNT")
public class PaymentDiscount extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PAYMENT_DISCOUNT_ID", nullable = false)
   private Long paymentDiscountId;

   @ManyToOne
   @JoinColumn(name = "PAYMENT_ID", nullable = false)
   private Payment payment;

   @Column(name = "PAYMENT_DISCOUNT_NAME", nullable = false)
   private String paymentDiscountName;

   @Column(name = "AMOUNT", nullable = false)
   private Long amount;


}
