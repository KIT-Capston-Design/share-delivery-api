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

   @ManyToOne
   @JoinColumn(name = "LEADER_ID", nullable = false)
   private Account leader;

   @ManyToOne
   @JoinColumn(name = "RECEIVING_LOCATION_ID", nullable = false)
   private ReceivingLocation receivingLocation;

   @ManyToOne
   @JoinColumn(name = "STORE_CATEGORY_ID", nullable = false)
   private StoreCategory storeCategory;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @Column(name = "LIMIT_PERSON", nullable = false)
   private Long limitPerson;

   @Column(name = "SHARE_STORE_LINK", nullable = false)
   private String shareStoreLink;

   @Column(name = "STORE_ID_KEY")
   private String storeIdKey;

   @Enumerated(EnumType.STRING)
   @Column(name = "LINK_PLATFORM_TYPE", nullable = false)
   private PlatformType linkPlatformType;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private DeliveryRoomState status;

   @OneToOne(mappedBy = "deliveryRoom")
   private Payment payment;

   @OneToMany(mappedBy = "deliveryRoom")
   private List<Chat> chats = new LinkedList<>();

   @OneToMany(mappedBy = "deliveryRoom")
   private List<EntryOrder> orders = new LinkedList<>();

   @OneToMany(mappedBy = "deliveryRoom")
   private List<Report> reports = new LinkedList<>();

   public void plusOrder(EntryOrder order){
      orders.add(order);
   }
}
