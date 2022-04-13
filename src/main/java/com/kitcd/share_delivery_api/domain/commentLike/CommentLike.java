package com.kitcd.share_delivery_api.domain.commentLike;

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
@Table(name = "COMMENT_LIKE")
public class CommentLike extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "COMMENT_LIKE_ID")
   private Long commentLikeId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "POST_ID")
   private Long postId;


}
