package com.kitcd.share_delivery_api.domain.fastDeliveryRoom;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

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
@Table(name = "FAST_DELIVERY_ROOM")
public class FastDeliveryRoom extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FAST_DELIVERY_ROOM_ID", nullable = false)
   private Long fastDeliveryRoomId;

   @Column(name = "USER_A_ID", nullable = false)
   private User userA;

   @Column(name = "USER_B_ID", nullable = false)
   private User userB;

   @Column(name = "IS_CLOSED", nullable = false)
   private String isClosed;


}
