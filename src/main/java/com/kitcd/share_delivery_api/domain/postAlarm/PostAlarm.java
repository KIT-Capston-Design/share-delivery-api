package com.kitcd.share_delivery_api.domain.postAlarm;

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
@Table(name = "POST_ALARM")
public class PostAlarm extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_ALARM_ID")
   private Long postAlarmId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "POST_ID")
   private Long postId;


}
