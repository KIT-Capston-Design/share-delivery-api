package com.kitcd.share_delivery_api.domain.postLike;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

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
@Table(name = "POST_LIKE")
public class PostLike extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_LIKE_ID", nullable = false)
   private Long postLikeId;

   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID", nullable = false)
   private Account account;

   @ManyToOne
   @JoinColumn(name = "POST_ID", nullable = false)
   private Post post;


}
