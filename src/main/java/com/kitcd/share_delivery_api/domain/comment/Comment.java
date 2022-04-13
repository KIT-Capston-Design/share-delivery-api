package com.kitcd.share_delivery_api.domain.comment;

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
@Table(name = "COMMENT")
public class Comment extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "COMMENT_ID")
   private Long commentId;

   @Column(name = "POST_ID")
   private Long postId;

   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "PARENT_ID")
   private Long parentId;

   @Column(name = "LIKE")
   private Long like;

   @Column(name = "CONTENT")
   private String content;

   @Column(name = "ANNOTATION")
   private String annotation;

   @Column(name = "STATUS")
   private String status;


}
