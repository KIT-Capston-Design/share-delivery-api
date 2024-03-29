package com.kitcd.share_delivery_api.domain.jpa.fastdeliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.chat.Chat;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.fastdeliveryparticipation.FastDeliveryParticipation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


//매칭은 레디스 통해 수행하고, 이 테이블은 기록 저장용으로 활용하는 것이 좋을 것 같다.
@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "FAST_DELIVERY_ROOM")
public class FastDeliveryRoom extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FAST_DELIVERY_ROOM_ID", nullable = false)
   private Long fastDeliveryRoomId;

   @Column(name = "Status", nullable = false)
   private String status;

   @OneToMany(mappedBy = "fastDeliveryRoom")
   private List<FastDeliveryParticipation> fastDeliveryParticipations = new LinkedList<>();

   @OneToMany(mappedBy = "fastDeliveryRoom")
   private List<Chat> chats = new LinkedList<>();
}
