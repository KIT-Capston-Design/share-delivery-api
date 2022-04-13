package com.kitcd.share_delivery_api.domain.deliveryRoom;

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
@Table(name = "DELIVERY_ROOM")
public class DeliveryRoom extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "DELIVERY_ROOM_ID")
   private Long deliveryRoomId;

   @Column(name = "LEADER_ID")
   private Long leaderId;

   @Column(name = "RECEIVING_LOCATION_ID")
   private Long receivingLocationId;

   @Column(name = "STORE_CATEGORY_ID")
   private Long storeCategoryId;

   @Column(name = "CONTENT")
   private String content;

   @Column(name = "LIMIT_PERSON")
   private Long limitPerson;

   @Column(name = "SHARE_STORE_LINK")
   private String shareStoreLink;

   @Column(name = "LINK_PLATFORM_TYPE")
   private String linkPlatformType;

   @Column(name = "STATUS")
   private String status;


}
