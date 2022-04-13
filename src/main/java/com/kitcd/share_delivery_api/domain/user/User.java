package com.kitcd.share_delivery_api.domain.user;

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
@Table(name = "USER")
public class User extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "PHONE_NUMBER")
   private String phoneNumber;

   @Column(name = "NICKNAME")
   private String nickname;

   @Column(name = "NAME")
   private String name;

   @Column(name = "PROFILE_IMAGE_ID")
   private String profileImageId;

   @Column(name = "EMAIL")
   private String email;

   @Column(name = "STATUS")
   private String status;

   @Column(name = "BANK")
   private String bank;

   @Column(name = "ACCOUNT")
   private String account;

   @Column(name = "RECENT_DATE")
   private java.time.LocalDateTime recentDate;


}
