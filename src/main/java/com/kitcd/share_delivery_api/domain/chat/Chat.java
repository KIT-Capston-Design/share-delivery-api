package com.kitcd.share_delivery_api.domain.chat;

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
@Table(name = "CHAT")
public class Chat extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "CHAT_ID")
   private Long chatId;

   @Column(name = "DELIVERY_ROOM_ID")
   private Long deliveryRoomId;

   @Column(name = "FAST_DELIVERY_ID")
   private Long fastDeliveryId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "TEXT")
   private String text;

   @Column(name = "ROOM_TYPE")
   private String roomType;

   @Column(name = "LATITUDE")
   private Double latitude;

   @Column(name = "LONGITUDE")
   private Double longitude;

   @Column(name = "IMAGE_FILE_ID")
   private Long imageFileId;

   @Column(name = "DTYPE")
   private Long dtype;


}
