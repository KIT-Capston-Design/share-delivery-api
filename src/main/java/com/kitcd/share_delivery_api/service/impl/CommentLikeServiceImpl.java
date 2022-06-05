package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLikeRepository;
import com.kitcd.share_delivery_api.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Override
    public Boolean isLiked(Long commentId, Long accountId) {
        Integer temp = commentLikeRepository.accountCommentLikeCheck(commentId, accountId);

        return !(temp == null);
    }
}
