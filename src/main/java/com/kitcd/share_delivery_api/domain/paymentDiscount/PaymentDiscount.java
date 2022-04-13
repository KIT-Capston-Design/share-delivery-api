package com.kitcd.share_delivery_api.domain.paymentDiscount;

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
@Table(name = "PAYMENT_DISCOUNT")
public class PaymentDiscount extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PAYMENT_DISCOUNT_ID")
   private Long paymentDiscountId;

   @Column(name = "PAYMENT_ID")
   private Long paymentId;

   @Column(name = "PAYMENT_DISCOUNT_NAME")
   private String paymentDiscountName;

   @Column(name = "AMOUNT")
   private Long amount;


}
