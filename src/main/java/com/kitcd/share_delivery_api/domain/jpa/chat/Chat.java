package com.kitcd.share_delivery_api.domain.jpa.chat;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.fastDeliveryRoom.FastDeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.imageFile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "CHAT")
public class Chat extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "CHAT_ID", nullable = false)
   private Long chatId;

   @ManyToOne
   @JoinColumn(name = "DELIVERY_ROOM_ID", nullable = true)
   private DeliveryRoom deliveryRoom;

   @ManyToOne
   @JoinColumn(name = "FAST_DELIVERY_ROOM_ID", nullable = true)
   private FastDeliveryRoom fastDeliveryRoom;

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @Column(name = "TEXT", nullable = true)
   private String text;

   @Embedded
   private Coordinate coordinate;

   @OneToOne
   @JoinColumn(name = "IMAGE_FILE_ID", nullable = true)
   private ImageFile imageFile;

   @Enumerated(EnumType.STRING)
   @Column(name = "ROOM_TYPE", nullable = false)
   private RoomType roomType;

   @Enumerated(EnumType.STRING)
   @Column(name = "CHAT_TYPE", nullable = false)
   private ChatType chatType;

   @OneToMany(mappedBy = "chat")
   private List<Report> reports = new LinkedList<>();


}
