package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.postlike.PostLikeDTO;

public interface PostLikeService {

    Boolean isPostLiked(Long accountId, Long postId);

    PostLikeDTO clickedLike(Long postId);
}
