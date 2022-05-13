package com.kitcd.share_delivery_api.domain.jpa.entryorder;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
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
@Table(name = "ENTRY_ORDER")
public class EntryOrder extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ENTRY_ORDER_ID", nullable = false)
   private Long entryOrderId;

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @ManyToOne
   @JoinColumn(name = "DELIVERY_ROOM_ID", nullable = false)
   private DeliveryRoom deliveryRoom;

   @Enumerated(EnumType.STRING)
   @Column(name = "ORDER_TYPE", nullable = false)
   private EntryOrderType orderType;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private State status;

   @OneToMany(mappedBy = "order")
   private List<OrderMenu> orderMenus = new LinkedList<>();

   public void accept() {
      if(status == State.PENDING)
         status = State.ACCEPTED;
   }

   public void reject() throws Exception {
      if(status == State.PENDING)
         status = State.REJECTED;
      else
         throw new Exception("Order status is not PENDING");

   }

}
