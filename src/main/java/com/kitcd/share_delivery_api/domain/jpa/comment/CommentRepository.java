package com.kitcd.share_delivery_api.domain.jpa.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findCommentsByPostId(Long postId);
}
