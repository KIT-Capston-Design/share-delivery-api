package com.kitcd.share_delivery_api.domain.comment;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.post.Post;
import com.kitcd.share_delivery_api.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "COMMENT")
public class Comment extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "COMMENT_ID", nullable = false)
   private Long commentId;

   @ManyToOne
   @JoinColumn(name = "POST_ID", nullable = false)
   private Post post;

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @OneToOne
   @JoinColumn(name = "PARENT_ID", nullable = true)
   private Comment parent;

   @Column(name = "LIKE_COUNT", nullable = false)
   private Long likeCount;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @ManyToOne
   @JoinColumn(name = "ANNOTATED_ACCOUNT", nullable = true)
   private Account annotatedAccount;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private State status;


}
