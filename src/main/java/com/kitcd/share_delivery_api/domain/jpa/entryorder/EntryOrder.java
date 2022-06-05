package com.kitcd.share_delivery_api.domain.jpa.entryorder;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
      else
         throw new IllegalStateException("Order status is not PENDING");
   }

   public void reject() {
      if(status == State.PENDING)
         status = State.REJECTED;
      else
         throw new IllegalStateException("Order status is not PENDING");
   }

   public void exitDeliveryRoom(){
      if(status == State.PENDING || status == State.ACCEPTED)
         status = State.CANCELLED;
      else
         throw new IllegalStateException("모집글을 퇴장할 수 있는 주문 상태가 아닙니다.");
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
              .nickName(account.getNickname())
              .type(orderType)
              .status(status)
              .menus(orderMenus.stream().map(OrderMenu::toResponseDTO).collect(Collectors.toList()))
              .createdDateTime(getCreatedDate())
              .build();
   }

   public Long getTotalPrice(){
      return getOrderMenus().stream()
              .map(menu-> menu.getPrice() * menu.getQuantity())
              .mapToLong(Long::longValue)
              .sum();
   }

   public Remittance toRemittanceEntity(Account recipient, Payment payment, Long additionalAmount){

      long value = getTotalPrice() + additionalAmount;

      //백원 이하 올림
      value = (long) (Math.ceil(((double)value)/100)) * 100;

      //0원 이하 0원으로
      if(value < 0) value = 0;

      return Remittance.builder()
              .remitter(getAccount())
              .recipient(recipient)
              .payment(payment)
              .amount(value)
              .isRemitted(false)
              .build();
   }
}
