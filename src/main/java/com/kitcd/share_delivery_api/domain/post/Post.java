package com.kitcd.share_delivery_api.domain.post;

import com.kitcd.share_delivery_api.domain.comment.Comment;
import com.kitcd.share_delivery_api.domain.commentLike.CommentLike;
import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.common.Coordinate;
import com.kitcd.share_delivery_api.domain.common.State;
import com.kitcd.share_delivery_api.domain.placeShare.PlaceShare;
import com.kitcd.share_delivery_api.domain.postAlarm.PostAlarm;
import com.kitcd.share_delivery_api.domain.postCategory.PostCategory;
import com.kitcd.share_delivery_api.domain.postImage.PostImage;
import com.kitcd.share_delivery_api.domain.postLike.PostLike;
import com.kitcd.share_delivery_api.domain.report.Report;
import com.kitcd.share_delivery_api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "POST")
public class Post extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_ID", nullable = false)
   private Long postId;

   @ManyToOne
   @JoinColumn(name = "USER_ID", nullable = false)
   private User user;

   @ManyToOne
   @JoinColumn(name = "POST_CATEGORY_ID", nullable = false)
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

   @OneToMany(mappedBy = "post")
   private List<Comment> comments = new LinkedList<>();

   @OneToOne(mappedBy = "post")
   private PlaceShare sharedPlace;

   @OneToMany(mappedBy = "post")
   private List<PostImage> images = new LinkedList<>();

   @OneToMany(mappedBy = "post")
   private List<PostLike> postLikes = new LinkedList<>();

   @OneToMany(mappedBy = "post")
   private List<CommentLike> commentLikes = new LinkedList<>();

   @OneToMany(mappedBy = "post")
   private List<PostAlarm> postAlarms = new LinkedList<>();

   @OneToMany(mappedBy = "post")
   private List<Report> reports = new LinkedList<>();


}
