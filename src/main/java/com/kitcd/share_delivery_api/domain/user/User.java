package com.kitcd.share_delivery_api.domain.user;

import com.kitcd.share_delivery_api.domain.userBlock.UserBlock;
import com.kitcd.share_delivery_api.domain.chat.Chat;
import com.kitcd.share_delivery_api.domain.comment.Comment;
import com.kitcd.share_delivery_api.domain.commentLike.CommentLike;
import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.fastDeliveryParticipation.FastDeliveryParticipation;
import com.kitcd.share_delivery_api.domain.friend.Friend;
import com.kitcd.share_delivery_api.domain.imageFile.ImageFile;
import com.kitcd.share_delivery_api.domain.order.Order;
import com.kitcd.share_delivery_api.domain.post.Post;
import com.kitcd.share_delivery_api.domain.postAlarm.PostAlarm;
import com.kitcd.share_delivery_api.domain.postLike.PostLike;
import com.kitcd.share_delivery_api.domain.receivingLocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.report.Report;
import com.kitcd.share_delivery_api.domain.userEvaluation.UserEvaluation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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

   @OneToOne
   @JoinColumn(name = "PROFILE_IMAGE_ID", nullable = false)
   private ImageFile profileImage;

   @Column(name = "EMAIL", nullable = false)
   private String email;

   @Column(name = "STATUS", nullable = false)
   private State status;

   @Embedded
   private BankAccount bankAccount;

   @Column(name = "LAST_LOGON_TIME", nullable = false)
   private LocalDateTime lastLogonTime;

   @OneToMany(mappedBy = "user")
   private List<Friend> friends = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<UserBlock> userBlocks = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<FastDeliveryParticipation> participations = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<ReceivingLocation> receivingLocations = new LinkedList<>();

   @OneToMany(mappedBy = "remitter")
   private List<Remittance> remittances = new LinkedList<>();

   @OneToMany(mappedBy = "recipient")
   private List<Remittance> collections = new LinkedList<>();

   @OneToMany(mappedBy = "leader")
   private List<DeliveryRoom> leadingDeliveryRoom = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<Chat> chats = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<Order> orders = new LinkedList<>();

   @OneToMany(mappedBy = "targetUser")
   private List<UserEvaluation> receivedEvaluations = new LinkedList<>();

   @OneToMany(mappedBy = "evaluator")
   private List<UserEvaluation> performedEvaluations = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<Post> posts = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<PostLike> postLikes = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<Comment> comments = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<CommentLike> commentLikes = new LinkedList<>();

   @OneToMany(mappedBy = "user")
   private List<PostAlarm> postAlarms = new LinkedList<>();

   @OneToMany(mappedBy = "reporter")
   private List<Report> receivedReports = new LinkedList<>();

   @OneToMany(mappedBy = "reportedUser")
   private List<Report> performedReport = new LinkedList<>();


}
