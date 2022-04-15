package com.kitcd.share_delivery_api.domain.report;

import com.kitcd.share_delivery_api.domain.chat.Chat;
import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.post.Post;
import com.kitcd.share_delivery_api.domain.reportCategory.ReportCategory;
import com.kitcd.share_delivery_api.domain.user.User;
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
   @Column(name = "REPORT_ID", nullable = false)
   private Long reportId;

   @ManyToOne
   @JoinColumn(name = "REPORT_CATEGORY_ID", nullable = false)
   private ReportCategory reportCategory;

   @ManyToOne
   @JoinColumn(name = "REPORTER_ID", nullable = false)
   private User reporter;

   @ManyToOne
   @JoinColumn(name = "REPORTED_USER_ID", nullable = false)
   private User reportedUser;

   @ManyToOne
   @JoinColumn(name = "POST_ID")
   private Post post;

   @ManyToOne
   @JoinColumn(name = "DELIVERY_ROOM_ID")
   private DeliveryRoom deliveryRoom;

   @ManyToOne
   @JoinColumn(name = "CHAT_ID")
   private Chat chat;

   @JoinColumn(name = "DESCRIPTION", nullable = false)
   private String description;


}
