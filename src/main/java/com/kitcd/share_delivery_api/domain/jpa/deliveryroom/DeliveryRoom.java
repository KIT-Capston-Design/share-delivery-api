package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.chat.Chat;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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

   @ManyToOne(fetch=FetchType.LAZY)
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

   public void addPerson() throws Exception {

      //방 인원 초과의 경우
      if(!(peopleNumber < getLimitPerson()))
         throw new Exception("exceed the number of limit people");

      this.peopleNumber = peopleNumber + 1;
   }
}
