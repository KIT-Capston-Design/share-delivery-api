package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.commentlike.CommentLikeDTO;

public interface CommentLikeService {
    Boolean isLiked(Long commentId, Long accountId);

    CommentLikeDTO clickedLike(Long commentId);
}
