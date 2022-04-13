package com.kitcd.share_delivery_api.domain.post;

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
@Table(name = "POST")
public class Post extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_ID")
   private Long postId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "POST_CATEGORY_ID")
   private Long postCategoryId;

   @Column(name = "CONTENT")
   private String content;

   @Column(name = "STATUS")
   private String status;

   @Column(name = "LIKE")
   private Long like;

   @Column(name = "VIEW")
   private Long view;

   @Column(name = "LATITUDE")
   private Double latitude;

   @Column(name = "LONGITUDE")
   private Double longitude;

   @Column(name = "CITY")
   private String city;

   @Column(name = "STREET")
   private String street;

   @Column(name = "ZIPCODE")
   private String zipcode;


}
