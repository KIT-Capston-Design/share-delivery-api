package com.kitcd.share_delivery_api.domain.comment;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.post.Post;
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
@Table(name = "COMMENT")
public class Comment extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "COMMENT_ID", nullable = false)
   private Long commentId;

   @Column(name = "POST_ID", nullable = false)
   private Post post;

   @Column(name = "USER_ID", nullable = false)
   private User user;

   @OneToOne
   @JoinColumn(name = "PARENT_ID", nullable = true)
   private Comment parent;

   @Column(name = "LIKE", nullable = false)
   private Long like;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @Column(name = "USER_ANNOTATION", nullable = false)
   private User userAnnotation;

   @Enumerated(EnumType.STRING)
   @Column(name = "STATUS", nullable = false)
   private State status;


}
