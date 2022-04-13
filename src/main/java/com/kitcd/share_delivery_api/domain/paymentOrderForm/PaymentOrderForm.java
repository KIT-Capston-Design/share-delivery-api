package com.kitcd.share_delivery_api.domain.paymentOrderForm;

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
@Table(name = "PAYMENT_ORDER_FORM")
public class PaymentOrderForm extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PAYMENT_ORDER_FORM_ID")
   private Long paymentOrderFormId;

   @Column(name = "PAYMENT_ID")
   private Long paymentId;

   @Column(name = "IMAGE_FILE_ID")
   private Long imageFileId;


}
