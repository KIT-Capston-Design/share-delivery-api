package com.kitcd.share_delivery_api.domain.jpa.payment;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PAYMENT_ID", nullable = false)
   private Long paymentId;

   @OneToOne
   @JoinColumn(name = "DELIVERY_ROOM_ID", nullable = false)
   private DeliveryRoom deliveryRoom;

   @Column(name = "DELIVERY_TIME", nullable = false)
   private Long deliveryTime;

   @Column(name = "DELIVERY_FEE", nullable = false)
   private Long deliveryFee;

   @OneToMany(mappedBy = "payment")
   private List<Remittance> remittances = new LinkedList<>();

   @OneToMany(mappedBy = "payment")
   private List<PaymentDiscount> paymentDiscounts = new LinkedList<>();

   @OneToMany(mappedBy = "payment")
   private List<PaymentOrderForm> paymentOrderForms = new LinkedList<>();
}
