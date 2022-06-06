package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.comment.CommentRepository;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLikeRepository;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLike;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLikeRepository;
import com.kitcd.share_delivery_api.dto.commentlike.CommentLikeDTO;
import com.kitcd.share_delivery_api.dto.postlike.PostLikeDTO;
import com.kitcd.share_delivery_api.service.CommentLikeService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    @Override
    public Boolean isLiked(Long commentId, Long accountId) {
        Integer temp = commentLikeRepository.accountCommentLikeCheck(commentId, accountId);

        return !(temp == null);
    }

    @Override
    public CommentLikeDTO clickedLike(Long commentId) {
        Comment findComment = commentRepository.findCommentByCommentId(commentId);
        CommentLikeDTO result;

        if(null ==findComment){
            throw new EntityNotFoundException("해당 덧글이 존재하지 않습니다.");
        }

        //postLike가 존재하는지 확인
        CommentLike findCommentLike = commentLikeRepository.getCommentLikeWithAccountIdAndCommentId(ContextHolder.getAccountId(), commentId);

        //존재한다면
        if(findCommentLike != null){
            findComment.decreaseLikeCount();
            commentRepository.save(findComment);
            result = CommentLike.toDTO(findComment.getCommentId(), findComment.getLikeCount(), false);
            commentLikeRepository.delete(findCommentLike);
        }else{
            findComment.increaseLikeCount();
            commentRepository.save(findComment);
            result = CommentLike.toDTO(findComment.getCommentId(), findComment.getLikeCount(), true);
            commentLikeRepository.save(CommentLikeDTO.toEntity(findComment));
        }

        return result;
    }
}
