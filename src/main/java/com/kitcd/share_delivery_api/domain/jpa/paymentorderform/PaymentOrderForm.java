package com.kitcd.share_delivery_api.domain.jpa.paymentorderform;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "PAYMENT_ORDER_FORM")
public class PaymentOrderForm extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PAYMENT_ORDER_FORM_ID", nullable = false)
   private Long paymentOrderFormId;

   @ManyToOne
   @JoinColumn(name = "PAYMENT_ID", nullable = false)
   private Payment payment;

   @OneToOne
   @JoinColumn(name = "IMAGE_FILE_ID", nullable = false)
   private ImageFile imageFile;


}
