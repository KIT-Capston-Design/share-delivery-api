package com.kitcd.share_delivery_api.domain.jpa.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kitcd.share_delivery_api.domain.jpa.accounblock.AccountBlock;
import com.kitcd.share_delivery_api.domain.jpa.chat.Chat;
import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.fastdeliveryparticipation.FastDeliveryParticipation;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.accountevaluation.AccountEvaluation;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postalarm.PostAlarm;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

   @Column(name = "NICKNAME", nullable = true, unique = true)
   private String nickname;

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

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<Friend> friends = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<AccountBlock> accountBlocks = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<FastDeliveryParticipation> participations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<ReceivingLocation> receivingLocations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "remitter")
   private List<Remittance> remittances = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "recipient")
   private List<Remittance> collections = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "leader")
   private List<DeliveryRoom> leadingDeliveryRoom = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<Chat> chats = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<EntryOrder> orders = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "targetAccount")
   private List<AccountEvaluation> receivedEvaluations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "evaluator")
   private List<AccountEvaluation> performedEvaluations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<Post> posts = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<PostLike> postLikes = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<Comment> comments = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<CommentLike> commentLikes = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account")
   private List<PostAlarm> postAlarms = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "reporter")
   private List<Report> receivedReports = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "reportedAccount")
   private List<Report> performedReport = new LinkedList<>();


   public void setDefaultNickname(){
      this.nickname = "유저" + accountId;
   }

   public void addEmail(String email){
      this.email = email;
   }
}
