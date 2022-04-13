package com.kitcd.share_delivery_api.domain.orderMenu;

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
@Table(name = "ORDER_MENU")
public class OrderMenu extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ORDER_MENU_ID")
   private Long orderMenuId;

   @Column(name = "ORDER_ID")
   private Long orderId;

   @Column(name = "PARENT_MENU_ID")
   private Long parentMenuId;

   @Column(name = "MENU_NAME")
   private String menuName;

   @Column(name = "AMOUNT")
   private Long amount;


}
