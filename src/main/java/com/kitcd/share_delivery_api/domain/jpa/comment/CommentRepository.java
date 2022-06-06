package com.kitcd.share_delivery_api.domain.jpa.comment;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(Post post);

    Comment findCommentByCommentId(Long commentId);
}
