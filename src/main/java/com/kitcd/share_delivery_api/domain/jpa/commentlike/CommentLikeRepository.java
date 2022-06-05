package com.kitcd.share_delivery_api.domain.jpa.commentlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("SELECT 1 FROM CommentLike cl WHERE cl.account.accountId = :accountId AND cl.comment.commentId =:commentId")
    Integer accountCommentLikeCheck(Long commentId, Long accountId);
}
