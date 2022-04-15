package com.kitcd.share_delivery_api.domain.deliveryRoom;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.receivingLocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.storeCategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.user.User;
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
@Table(name = "DELIVERY_ROOM")
public class DeliveryRoom extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "DELIVERY_ROOM_ID", nullable = false)
   private Long deliveryRoomId;

   @Column(name = "LEADER_ID", nullable = false)
   private User leader;

   @Column(name = "RECEIVING_LOCATION_ID", nullable = false)
   private ReceivingLocation receivingLocation;

   @Column(name = "STORE_CATEGORY_ID", nullable = false)
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


}
