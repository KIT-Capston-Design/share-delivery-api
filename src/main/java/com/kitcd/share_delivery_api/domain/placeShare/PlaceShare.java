package com.kitcd.share_delivery_api.domain.placeShare;

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
@Table(name = "PLACE_SHARE")
public class PlaceShare extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PLACE_SHARE_ID")
   private Long placeShareId;

   @Column(name = "POST_ID")
   private Long postId;

   @Column(name = "CONTENT")
   private String content;

   @Column(name = "LATITUDE")
   private Double latitude;

   @Column(name = "LONGITUDE")
   private Double longitude;

   @Column(name = "ADDRESS")
   private String address;


}
