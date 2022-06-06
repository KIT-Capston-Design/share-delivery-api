package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.chat.Chat;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import com.kitcd.share_delivery_api.dto.payment.FinalOrderInformationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "DELIVERY_ROOM")
public class DeliveryRoom extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "DELIVERY_ROOM_ID", nullable = false)
   private Long deliveryRoomId;

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name = "LEADER_ID", nullable = false)
   private Account leader;

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name = "RECEIVING_LOCATION_ID", nullable = false)
   private ReceivingLocation receivingLocation;

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name = "STORE_CATEGORY_ID", nullable = false)
   private StoreCategory storeCategory;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @Column(name = "PEOPLE_NUMBER", nullable = false)
   private Long peopleNumber;

   @Column(name = "LIMIT_PERSON", nullable = false)
   private Long limitPerson;

   @Column(name = "ESTIMATED_DELIVERY_TIP", nullable = false)
   private Long estimatedDeliveryTip;

   @Column(name = "STORE_NAME", nullable = false)
   private String storeName;

   @Column(name = "STORE_LINK", nullable = false)
   private String storeLink;

   @Column(name = "STORE_ID_KEY")
   private String storeIdKey;

   @Enumerated(EnumType.STRING)
   @Column(name = "LINK_PLATFORM_TYPE", nullable = false)
   private PlatformType linkPlatformType;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private DeliveryRoomState status;

   @OneToOne(mappedBy = "deliveryRoom", fetch = FetchType.LAZY)
   private Payment payment;

   @OneToMany(mappedBy = "deliveryRoom", fetch = FetchType.LAZY)
   private List<Chat> chats = new LinkedList<>();

   @OneToMany(mappedBy = "deliveryRoom", fetch = FetchType.LAZY)
   private List<EntryOrder> orders = new LinkedList<>();

   @OneToMany(mappedBy = "deliveryRoom", fetch = FetchType.LAZY)
   private List<Report> reports = new LinkedList<>();

   public void plusOrder(EntryOrder order){
      orders.add(order);
   }

   public void addPerson() {

      //방 인원 초과의 경우
      if(!(peopleNumber < getLimitPerson()))
         throw new IllegalStateException("exceed the number of limit people");

      this.peopleNumber = peopleNumber + 1;
   }

   public DeliveryRoomDTO toDTO() {
      return DeliveryRoomDTO.builder()
              .leader(SimpleAccountDTO.builder()
                      .accountId(leader.getAccountId())
                      .nickname(leader.getNickname())
                      .mannerScore(leader.getMannerScore())
                      .build())
              .roomId(deliveryRoomId)
              .content(content)
              .deliveryTip(estimatedDeliveryTip)
              .person(peopleNumber)
              .limitPerson(limitPerson)
              .storeName(storeName)
              .storeCategory(storeCategory.getCategoryName())
              .storeLink(storeLink)
              .platformType(linkPlatformType)
              .status(status)
              .storeCategory(storeCategory.getCategoryName())
              .createdDateTime(getCreatedDate())
              .receivingLocation(receivingLocation.toLocationDTO())
              .build();
   }
   public void closeRecruit(){
      if(status.equals(DeliveryRoomState.OPEN))
         status = DeliveryRoomState.WAITING_PAYMENT;
      else
         throw new IllegalStateException("모집글이 Open 상태가 아니기 때문에 요청을 처리할 수 없습니다.");
   }

   public void checkLeader(Long accountId) {
      if(!leader.getAccountId().equals(accountId)) throw new AccessDeniedException("Access is Denied");
   }

   public void delete() {
      if(!(status.equals(DeliveryRoomState.OPEN) || status.equals(DeliveryRoomState.WAITING_PAYMENT))){
         throw new IllegalStateException("모집글을 삭제할 수 있는 상태가 아닙니다.");
      }
      status = DeliveryRoomState.DELETED;
   }

   //주도자를 제외한 참여자들의 주문 가져오기
   public List<EntryOrder> getParticipantsOrder(){
      return getOrders().stream()
              .filter(order -> (order.getStatus() == State.ACCEPTED) && (!Objects.equals(order.getAccount().getAccountId(), leader.getAccountId())))
              .collect(Collectors.toList());
   }

   public DeliveryRoomState enrollPaymentStatusCheck() {
      if(!status.equals(DeliveryRoomState.WAITING_PAYMENT)) throw new IllegalStateException("주문서를 등록할 수 있는 모집글 상태가 아닙니다.");

      return status = DeliveryRoomState.WAITING_DELIVERY;
   }

   public FinalOrderInformationDTO toFinalOrderInformationDTO(Payment payment, List<OrderResDTO> orders, List<String> imageUrls) {
      return FinalOrderInformationDTO.builder()
              .roomId(deliveryRoomId)
              .orders(orders)
              .discounts(payment.getPaymentDiscounts())
              .totalDiscountAmount(payment.getTotalDiscountAmount())
              .deliveryFee(payment.getDeliveryFee())
              .orderFormUrlList(imageUrls)
              .receivingLocation(receivingLocation.toLocationDTO())
              .leader(SimpleAccountDTO.builder()
                      .accountId(getLeader().getAccountId())
                      .nickname(getLeader().getNickname())
                      .bankAccount(getLeader().getBankAccount().toDTO())
                      .build()
              ).build();
   }

   public DeliveryRoomState deliveryComplete() {

      if(!(status.equals(DeliveryRoomState.WAITING_DELIVERY))){
         throw new IllegalStateException("배달 대기 상태가 아니기에 배달 완료 작업을 수행할 수 없습니다.");
      }

      return status = DeliveryRoomState.WAITING_REMITTANCE;
   }

   public DeliveryRoomState remittancesComplete() {
      if(!(status.equals(DeliveryRoomState.WAITING_REMITTANCE))){
         throw new IllegalStateException("송금 대기 상태 이외의 상태에서 모집글 완료 상태로 이전 불가능");
      }
      return status = DeliveryRoomState.COMPLETED;
   }
}
