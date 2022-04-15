package com.kitcd.share_delivery_api.domain.post;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.Coordinate;
import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.postCategory.PostCategory;
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
@Table(name = "POST")
public class Post extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_ID", nullable = false)
   private Long postId;

   @Column(name = "USER_ID", nullable = false)
   private User user;

   @Column(name = "POST_CATEGORY_ID", nullable = false)
   private PostCategory postCategory;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @Enumerated
   @Column(name = "STATUS", nullable = false)
   private State status;

   @Column(name = "LIKE", nullable = false)
   private Long like;

   @Column(name = "VIEW_COUNT", nullable = false)
   private Long view_count;

   @Embedded
   private Coordinate coordinate;

   @Column(name = "CITY", nullable = false)
   private String Address;

}
