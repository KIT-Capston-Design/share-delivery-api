package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLikeRepository;
import com.kitcd.share_delivery_api.dto.postlike.PostLikeDTO;
import com.kitcd.share_delivery_api.service.PostLikeService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    @Override
    public Boolean isPostLiked(Long accountId, Long postId) {
        Integer temp = postLikeRepository.accountPostLikeCheck(accountId, postId);

        return !(temp == null);
    }

    //처음에 클릭하면 생성, 존재한다면 삭제.
    @Override
    public PostLikeDTO clickedLike(Long postId) {
        Post findPost = postRepository.findPostByPostId(postId);
        PostLikeDTO result;

        if(null == findPost){
            throw new EntityNotFoundException("해당 post가 존재하지 않습니다.");
        }

        //postLike가 존재하는지 확인
        PostLike findPostLike = postLikeRepository.getPostLikeWithAccountIdAndPostId(ContextHolder.getAccountId(), postId);

        //존재한다면
        if(findPostLike != null){
            findPost.decreaseLikeCount();
            postRepository.save(findPost);
            result = PostLike.toDTO(findPost.getLikeCount(), false, postId);
            postLikeRepository.delete(findPostLike);
        }else{
            findPost.increaseLikeCount();
            postRepository.save(findPost);
            result = PostLike.toDTO(findPost.getLikeCount(), true, postId);
            postLikeRepository.save(PostLikeDTO.toEntity(findPost));
        }

        return result;
    }
}
