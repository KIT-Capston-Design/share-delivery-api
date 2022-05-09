package com.kitcd.share_delivery_api.domain.jpa.receivinglocation;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.utils.geometry.Location;
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
@Table(name = "RECEIVING_LOCATION")
public class ReceivingLocation extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "RECEIVING_LOCATION_ID", nullable = false)
   private Long receivingLocationId;

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @Column(name = "IS_FAVORITE", nullable = false)
   private Boolean isFavorite;

   @Column(name = "DESCRIPTION", nullable = false)
   private String description;

   @Embedded
   private Location location;

   @Column(name = "ADDRESS", nullable = false)
   private String address;

   @OneToMany(mappedBy = "receivingLocation")
   private List<DeliveryRoom> deliveryRooms = new LinkedList<>();


}
