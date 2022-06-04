package com.kitcd.share_delivery_api.service;

public interface CommentLikeService {
    public Boolean isLiked(Long commentId, Long accountId);
}
