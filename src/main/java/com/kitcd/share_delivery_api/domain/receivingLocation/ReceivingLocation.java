package com.kitcd.share_delivery_api.domain.receivingLocation;

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
@Table(name = "RECEIVING_LOCATION")
public class ReceivingLocation extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "RECEIVING_LOCATION_ID")
   private Long receivingLocationId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "IS_FAVORITE")
   private String isFavorite;

   @Column(name = "NAME")
   private String name;

   @Column(name = "LATITUDE")
   private Double latitude;

   @Column(name = "LONGITUDE")
   private Double longitude;

   @Column(name = "ADDRESS")
   private String address;


}
