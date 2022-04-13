package com.kitcd.share_delivery_api.domain.fastDeliveryRoom;

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
@Table(name = "FAST_DELIVERY_ROOM")
public class FastDeliveryRoom extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FAST_DELIVERY_ROOM_ID")
   private Long fastDeliveryRoomId;

   @Column(name = "USER1_ID")
   private Long user1Id;

   @Column(name = "USER2_ID")
   private Long user2Id;

   @Column(name = "IS_CLOSED")
   private String isClosed;


}
