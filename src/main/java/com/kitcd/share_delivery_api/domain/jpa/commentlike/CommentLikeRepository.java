package com.kitcd.share_delivery_api.domain.jpa.commentlike;

import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("SELECT 1 FROM CommentLike cl WHERE cl.account.accountId = :accountId AND cl.comment.commentId =:commentId")
    Integer accountCommentLikeCheck(Long commentId, Long accountId);

    @Query("SELECT ck FROM CommentLike ck WHERE ck.account.accountId = :accountId AND ck.comment.commentId =:commentId")
    CommentLike getCommentLikeWithAccountIdAndCommentId(Long accountId, Long commentId);
}
