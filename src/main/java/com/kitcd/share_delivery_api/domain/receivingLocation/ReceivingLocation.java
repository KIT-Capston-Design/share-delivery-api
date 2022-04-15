package com.kitcd.share_delivery_api.domain.receivingLocation;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.Coordinate;
import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.paymentOrderForm.PaymentOrderForm;
import com.kitcd.share_delivery_api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "RECEIVING_LOCATION")
public class ReceivingLocation extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "RECEIVING_LOCATION_ID", nullable = false)
   private Long receivingLocationId;

   @ManyToOne
   @JoinColumn(name = "USER_ID", nullable = false)
   private User user;

   @Column(name = "IS_FAVORITE", nullable = false)
   private String isFavorite;

   @Column(name = "NAME", nullable = false)
   private String name;

   @Embedded
   private Coordinate coordinate;

   @Column(name = "ADDRESS", nullable = false)
   private String address;

   @OneToMany(mappedBy = "receivingLocation")
   private List<DeliveryRoom> deliveryRooms = new LinkedList<>();


}
