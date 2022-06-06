package com.kitcd.share_delivery_api.domain.jpa.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Post findPostByPostId(Long postId);

}
