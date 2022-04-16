package com.kitcd.share_delivery_api.domain.account;

import com.kitcd.share_delivery_api.domain.accountBlock.AccountBlock;
import com.kitcd.share_delivery_api.domain.chat.Chat;
import com.kitcd.share_delivery_api.domain.comment.Comment;
import com.kitcd.share_delivery_api.domain.commentLike.CommentLike;
import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.fastDeliveryParticipation.FastDeliveryParticipation;
import com.kitcd.share_delivery_api.domain.friend.Friend;
import com.kitcd.share_delivery_api.domain.imageFile.ImageFile;
import com.kitcd.share_delivery_api.domain.entryOrder.EntryOrder;
import com.kitcd.share_delivery_api.domain.post.Post;
import com.kitcd.share_delivery_api.domain.postAlarm.PostAlarm;
import com.kitcd.share_delivery_api.domain.postLike.PostLike;
import com.kitcd.share_delivery_api.domain.receivingLocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.report.Report;
import com.kitcd.share_delivery_api.domain.accountEvaluation.AccountEvaluation;
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
@Table(name = "ACCOUNT")
public class Account extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ACCOUNT_ID", nullable = false)
   private Long accountId;

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

   @OneToMany(mappedBy = "account")
   private List<Friend> friends = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<AccountBlock> accountBlocks = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<FastDeliveryParticipation> participations = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<ReceivingLocation> receivingLocations = new LinkedList<>();

   @OneToMany(mappedBy = "remitter")
   private List<Remittance> remittances = new LinkedList<>();

   @OneToMany(mappedBy = "recipient")
   private List<Remittance> collections = new LinkedList<>();

   @OneToMany(mappedBy = "leader")
   private List<DeliveryRoom> leadingDeliveryRoom = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<Chat> chats = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<EntryOrder> orders = new LinkedList<>();

   @OneToMany(mappedBy = "targetAccount")
   private List<AccountEvaluation> receivedEvaluations = new LinkedList<>();

   @OneToMany(mappedBy = "evaluator")
   private List<AccountEvaluation> performedEvaluations = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<Post> posts = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<PostLike> postLikes = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<Comment> comments = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<CommentLike> commentLikes = new LinkedList<>();

   @OneToMany(mappedBy = "account")
   private List<PostAlarm> postAlarms = new LinkedList<>();

   @OneToMany(mappedBy = "reporter")
   private List<Report> receivedReports = new LinkedList<>();

   @OneToMany(mappedBy = "reportedAccount")
   private List<Report> performedReport = new LinkedList<>();


}
