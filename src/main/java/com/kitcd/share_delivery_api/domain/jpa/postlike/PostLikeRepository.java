package com.kitcd.share_delivery_api.domain.jpa.postlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query("SELECT 1 FROM PostLike p WHERE p.account.accountId = :accountId AND p.post.postId =:postId")
    Integer accountPostLikeCheck(Long accountId, Long postId);

    @Query("SELECT pk FROM PostLike pk WHERE pk.account.accountId = :accountId AND pk.post.postId =:postId")
    PostLike getPostLikeWithAccountIdAndPostId(Long accountId, Long postId);
}
