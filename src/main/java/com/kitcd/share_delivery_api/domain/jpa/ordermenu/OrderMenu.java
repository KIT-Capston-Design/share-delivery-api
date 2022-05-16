package com.kitcd.share_delivery_api.domain.jpa.ordermenu;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;

import com.kitcd.share_delivery_api.dto.ordermenu.MenuResDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

   @ManyToOne
   @JoinColumn(name = "PARENT_MENU_ID", nullable = true)
   private OrderMenu parentMenu;

   @Column(name = "MENU_NAME", nullable = false)
   private String menuName;

   @Column(name = "QUANTITY", nullable = false)
   private Long quantity;

   @Column(name = "PRICE", nullable = false)
   private Long price;

   @Transient
   private List<OrderMenu> childMenus = new LinkedList<>();

   public MenuResDTO toResponseDTO(){
      return MenuResDTO.builder()
              .orderMenuId(orderMenuId)
              .menuName(menuName)
              .quantity(quantity)
              .price(price)
              .optionMenus(childMenus != null ? childMenus.stream().map(OrderMenu::toResponseDTO).collect(Collectors.toList()) : null)
              .build();
   }

   public void addChild(OrderMenu menu) {
      childMenus.add(menu);
   }
}
