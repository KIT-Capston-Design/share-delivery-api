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
import com.kitcd.share_delivery_api.dto.account.AccountDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

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

   @OneToOne(fetch = FetchType.LAZY)
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

   @Column(name = "manner_score", nullable = false)// 자동 계산 되도록.
   @ColumnDefault("36.5") // default값 설정.
   private Double mannerScore;
   @Embedded
   private BankAccount bankAccount;

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<Friend> friends = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<AccountBlock> accountBlocks = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<FastDeliveryParticipation> participations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<ReceivingLocation> receivingLocations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "remitter", fetch = FetchType.LAZY)
   private List<Remittance> remittances = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
   private List<Remittance> collections = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
   private List<DeliveryRoom> leadingDeliveryRoom = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<Chat> chats = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<EntryOrder> orders = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "targetAccount", fetch = FetchType.LAZY)
   private List<AccountEvaluation> receivedEvaluations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "evaluator", fetch = FetchType.LAZY)
   private List<AccountEvaluation> performedEvaluations = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<Post> posts = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<PostLike> postLikes = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<Comment> comments = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<CommentLike> commentLikes = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
   private List<PostAlarm> postAlarms = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY)
   private List<Report> receivedReports = new LinkedList<>();

   @JsonIgnore
   @OneToMany(mappedBy = "reportedAccount", fetch = FetchType.LAZY)
   private List<Report> performedReport = new LinkedList<>();


   public void setDefaultNickname(){
      this.nickname = "유저" + accountId;
   }

   public void addEmail(String email){
      this.email = email;
   }

   public Account saveBankAccount(BankAccount bankAccount) {
      this.bankAccount = bankAccount;
      return this;
   }

   public AccountDTO toDTO(){
      return AccountDTO.builder()
              .createdDate(getCreatedDate())
              .modifiedDate(getModifiedDate())
              .accountId(accountId)
              .phoneNumber(phoneNumber)
              .nickname(nickname)
              .profileImage(profileImage)
              .email(email)
              .status(status)
              .role(role)
              .bankAccount(bankAccount.toDTO())
              .build();
   }
}
