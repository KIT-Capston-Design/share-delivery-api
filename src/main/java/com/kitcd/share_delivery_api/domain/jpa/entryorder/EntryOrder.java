package com.kitcd.share_delivery_api.domain.jpa.entryorder;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@ToString(exclude = {"orderMenus", "account", "deliveryRoom"})
@Table(name = "ENTRY_ORDER")
public class EntryOrder extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ENTRY_ORDER_ID", nullable = false)
   private Long entryOrderId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "DELIVERY_ROOM_ID", nullable = false)
   private DeliveryRoom deliveryRoom;

   @Enumerated(EnumType.STRING)
   @Column(name = "ORDER_TYPE", nullable = false)
   private EntryOrderType orderType;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private State status;

   @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
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


   public OrderResDTO toResponseDTO(){

      //option 메뉴는 리스트에서 삭제하고 부모 메뉴의 자식 리스트에 추가
      orderMenus.stream()
              .filter(menu -> menu.getParentMenu() != null)
              .forEach(menu -> menu.getParentMenu().addChild(menu));
      orderMenus.removeIf(menu -> menu.getParentMenu() != null);


      return OrderResDTO.builder()
              .entryOrderId(entryOrderId)
              .accountId(account.getAccountId())
              .phoneNumber(account.getPhoneNumber())
              .type(orderType)
              .status(status)
              .menus(orderMenus.stream().map(OrderMenu::toResponseDTO).collect(Collectors.toList()))
              .createdDateTime(getCreatedDate())
              .build();
   }

}
