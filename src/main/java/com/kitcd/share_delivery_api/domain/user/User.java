package com.kitcd.share_delivery_api.domain.user;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.imageFile.ImageFile;
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
   @Column(name = "USER_ID", nullable = false)
   private Long userId;

   @Column(name = "PHONE_NUMBER", nullable = false)
   private String phoneNumber;

   @Column(name = "NICKNAME", nullable = false)
   private String nickname;

   @Column(name = "NAME", nullable = false)
   private String name;

   @Column(name = "PROFILE_IMAGE_ID", nullable = false)
   private ImageFile profileImage;

   @Column(name = "EMAIL", nullable = false)
   private String email;

   @Column(name = "STATUS", nullable = false)
   private State status;

   @Embedded
   private BankAccount bankAccount;

   @Column(name = "LAST_LOGON_TIME", nullable = false)
   private LocalDateTime lastLogonTime;


}
