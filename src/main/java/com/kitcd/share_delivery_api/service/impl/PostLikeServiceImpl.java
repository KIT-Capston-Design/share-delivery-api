package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLikeRepository;
import com.kitcd.share_delivery_api.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    @Override
    public Boolean isPostLiked(Long accountId, Long postId) {
        Integer temp = postLikeRepository.accountPostLikeCheck(accountId, postId);

        return !(temp == null);
    }
}
