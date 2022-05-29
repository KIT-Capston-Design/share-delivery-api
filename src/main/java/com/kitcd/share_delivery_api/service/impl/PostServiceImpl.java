package com.kitcd.share_delivery_api.service.impl;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShareRepository;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategoryRepository;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostResponseDTO;
import com.kitcd.share_delivery_api.service.PostService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    private final PlaceShareRepository placeShareRepository;

    @Override
    public WritePostResponseDTO writePost(WritePostRequestDTO dto) {
        PostCategory category = postCategoryRepository.findByCategoryName(dto.getCategory());

        Post post = postRepository.save(dto.toEntity(category));

        PlaceShare placeShare = placeShareRepository.save(dto.getSharePlace().toEntity(post));


        Account account = ContextHolder.getAccount();

        return WritePostResponseDTO.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .createdDateTime(post.getCreatedDate())
                .writer(WritePostResponseDTO.Writer.builder()
                        .accountId(account.getAccountId())
                        .mannerScore(account.getMannerScore())
                        .nickName(account.getNickname())
                        .build())
                .category(category.getCategoryName())
                .postDetail(WritePostResponseDTO.PostDetail.builder()
                        .coordinate(placeShare.getCoordinate()).
                        build())
                .build();
    }
}
