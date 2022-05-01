package com.kitcd.share_delivery_api.domain.jpa.post;

import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.postalarm.PostAlarm;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImage;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
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
@Table(name = "POST")
public class Post extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_ID", nullable = false)
   private Long postId;

   @ManyToOne
   @JoinColumn(name = "USER_ID", nullable = false)
   private Account account;

   @ManyToOne
   @JoinColumn(name = "POST_CATEGORY_ID", nullable = false)
   private PostCategory postCategory;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @Enumerated
   @Column(name = "STATUS", nullable = false)
   private State status;

   @Column(name = "LIKE_COUNT", nullable = false)
   private Long likeCount;

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
