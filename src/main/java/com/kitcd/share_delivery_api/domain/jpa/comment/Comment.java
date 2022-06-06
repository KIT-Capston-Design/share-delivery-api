package com.kitcd.share_delivery_api.domain.jpa.comment;

import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.comment.CommentDTO;
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

   @OneToMany(mappedBy = "comment")
   private List<CommentLike> commentLikes = new LinkedList<>();

   public void addLikePerson(){
      this.likeCount += likeCount;
   }

   public void increaseLikeCount(){
      likeCount += 1;
   }
   public void decreaseLikeCount() {
      likeCount -= 1;
   }
   public CommentDTO toDTO(Boolean isLiked){
      return CommentDTO.builder()
              .id(commentId)
              .createdDateTime(getCreatedDate())
              .isLiked(isLiked)
              .likes(likeCount)
              .writer(SimpleAccountDTO.builder()
                      .accountId(account.getAccountId())
                      .mannerScore(account.getMannerScore())
                      .nickname(account.getNickname())
                      .build())
              .parentId(!(null==parent) ? parent.getCommentId() : commentId)
              .content(content)
              .state(status)
              .build();
   }

   public void update(String content){
      this.content = content;
   }

   public void delete(){
      this.status = State.DELETED;
   }
}
