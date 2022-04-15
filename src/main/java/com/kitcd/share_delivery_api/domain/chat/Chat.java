package com.kitcd.share_delivery_api.domain.chat;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.Coordinate;
import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.fastDeliveryRoom.FastDeliveryRoom;
import com.kitcd.share_delivery_api.domain.imageFile.ImageFile;
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
@Table(name = "CHAT")
public class Chat extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "CHAT_ID", nullable = false)
   private Long chatId;

   @Column(name = "DELIVERY_ROOM_ID", nullable = true)
   private DeliveryRoom deliveryRoom;

   @Column(name = "FAST_DELIVERY_ROOM_ID", nullable = true)
   private FastDeliveryRoom fastDeliveryRoom;

   @Column(name = "USER_ID", nullable = false)
   private User user;

   @Column(name = "TEXT", nullable = true)
   private String text;

   @Embedded
   private Coordinate coordinate;

   @Column(name = "IMAGE_FILE_ID", nullable = true)
   private ImageFile imageFile;

   @Enumerated(EnumType.STRING)
   @Column(name = "ROOM_TYPE", nullable = false)
   private RoomType roomType;

   @Enumerated(EnumType.STRING)
   @Column(name = "CHAT_TYPE", nullable = false)
   private ChatType chatType;


}
