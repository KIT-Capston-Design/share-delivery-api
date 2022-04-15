package com.kitcd.share_delivery_api.domain.orderMenu;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.evaluationCategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.order.Order;
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
   @Column(name = "ORDER_MENU_ID", nullable = false)
   private Long orderMenuId;

   @Column(name = "ORDER_ID", nullable = false)
   private Order order;

   @OneToOne
   @JoinColumn(name = "PARENT_MENU_ID")
   private OrderMenu parentMenu;

   @Column(name = "MENU_NAME", nullable = false)
   private String menuName;

   @Column(name = "AMOUNT", nullable = false)
   private Long amount;


}
