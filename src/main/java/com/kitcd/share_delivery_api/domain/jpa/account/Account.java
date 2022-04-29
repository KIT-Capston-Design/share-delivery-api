package com.kitcd.share_delivery_api.domain.jpa.account;

import com.kitcd.share_delivery_api.domain.jpa.accountBlock.AccountBlock;
import com.kitcd.share_delivery_api.domain.jpa.chat.Chat;
import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.commentLike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.fastDeliveryParticipation.FastDeliveryParticipation;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.domain.jpa.imageFile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.entryOrder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.accountEvaluation.AccountEvaluation;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postAlarm.PostAlarm;
import com.kitcd.share_delivery_api.domain.jpa.postLike.PostLike;
import com.kitcd.share_delivery_api.domain.jpa.receivingLocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

   @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
   private String phoneNumber;

   @Column(name = "NICKNAME", nullable = true)
   private String nickname;

   @Column(name = "NAME", nullable = true)
   private String name;

   @OneToOne
   @JoinColumn(name = "PROFILE_IMAGE_ID", nullable = true)
   private ImageFile profileImage;

   @Column(name = "EMAIL", nullable = true)
   private String email;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = true)
   private State status;

   @Enumerated(EnumType.STRING)
   @Column(name = "ROLE", nullable = true)
   private RoleType role;

   @Embedded
   private BankAccount bankAccount;

   @Column(name = "LAST_LOGON_TIME", nullable = true)
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
