package com.kitcd.share_delivery_api.domain.orderMenu;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.entryOrder.EntryOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "ORDER_MENU")
public class OrderMenu extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ORDER_MENU_ID", nullable = false)
   private Long orderMenuId;

   @ManyToOne
   @JoinColumn(name = "ORDER_ID", nullable = false)
   private EntryOrder order;

   @OneToOne
   @JoinColumn(name = "PARENT_MENU_ID")
   private OrderMenu parentMenu;

   @Column(name = "MENU_NAME", nullable = false)
   private String menuName;

   @Column(name = "AMOUNT", nullable = false)
   private Long amount;


}
