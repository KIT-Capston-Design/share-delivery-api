package com.kitcd.share_delivery_api.domain.report;

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
@Table(name = "REPORT")
public class Report extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "REPORT_ID")
   private Long reportId;

   @Column(name = "REPORT_CATEGORY_ID")
   private Long reportCategoryId;

   @Column(name = "REPORTER_ID")
   private Long reporterId;

   @Column(name = "REPORTED_USER_ID")
   private Long reportedUserId;

   @Column(name = "DELIVERY_ROOM_ID")
   private Long deliveryRoomId;

   @Column(name = "CHAT_ID")
   private Long chatId;

   @Column(name = "DESCRIPTION")
   private String description;

   @Column(name = "DTYPE")
   private Long dtype;


}
