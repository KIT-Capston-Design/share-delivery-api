package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.comment.CommentRepository;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLike;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLikeRepository;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.dto.comment.CommentDTO;
import com.kitcd.share_delivery_api.dto.comment.CommentWriteDTO;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.service.CommentLikeService;
import com.kitcd.share_delivery_api.service.CommentService;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import com.kitcd.share_delivery_api.service.LoggedOnInformationService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final LoggedOnInformationService loggedOnInformationService;
    private final PostRepository postRepository;
    private final CommentLikeService commentLikeService;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentDTO writeComment(CommentWriteDTO dto) {
        Optional<Post> findPost = postRepository.findById(dto.getPostId());
        if (findPost.isEmpty()){
            throw new EntityNotFoundException("해당 post를 찾을 수 없습니다.");
        }

        //공통부분 실제타입 선언
        Comment parent = null;
        Long fcmTargetId;
        String title;
        HashMap<String, Object> data = new HashMap<>();
        data.put("postId", dto.getPostId());
        Comment comment;

        //대댓글
        if (dto.getParentId() != null){
            parent = getComment(dto.getParentId());
            fcmTargetId = parent.getAccount().getAccountId();

            title = "내가 작성한 댓글에 대댓글이 달렸어요!";
            data.put("type", FCMDataType.COMMENT_PLUS);
            data.put("parentId", dto.getParentId());

        //댓글
        }else {
            title = "내가 작성한 글에 댓글이 달렸어요!";
            data.put("type", FCMDataType.POST_COMMENT);
            fcmTargetId = findPost.get().getAccount().getAccountId();
        }

        comment = commentRepository.save(dto.toEntity(parent, findPost.get()));

        firebaseCloudMessageService.sendMessageTo(
                loggedOnInformationService.getFcmTokenByAccountId(fcmTargetId),
                title, comment.getContent(),data);

        return comment.toDTO(false); // 처음은 무조건 false;
    }



    @Override
    public Comment getComment(Long id) {
        Optional<Comment> findComment = commentRepository.findById(id);

        if(findComment.isEmpty()){
            throw new EntityNotFoundException("Comment is not exist");
        }

        return findComment.get();
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Long nowSessionId = ContextHolder.getAccountId();
        Optional<Post> findPost = postRepository.findById(postId);

        if(findPost.isEmpty()){
            throw new EntityNotFoundException("해당하는 게시글이 없습니다.");
        }

        List<CommentDTO> findList = commentRepository.findCommentsByPost(findPost.get()).stream()
                .map(i ->{
                    Boolean isLiked = commentLikeService.isLiked(i.getCommentId(), nowSessionId);
                    return i.toDTO(isLiked);
                })
                .collect(Collectors.toList());
        return findList;
    }

    @Override
    public CommentDTO updateComment(Long commentId, String content) {
        Optional<Comment> findComment = commentRepository.findById(commentId);

        if(findComment.isEmpty()){
            throw new EntityNotFoundException(Comment.class.toString());
        }
        //유저 id가 서로 다른 경우
        if(!ContextHolder.getAccountId()
                .equals(findComment.get().getAccount().getAccountId())){
            throw new AccessDeniedException("부적절한 접근입니다.");
        }

        findComment.get().update(content);

        commentRepository.save(findComment.get());

        Boolean isLiked = commentLikeService.isLiked(commentId, ContextHolder.getAccountId());
        return findComment.get().toDTO(isLiked);
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);

        if(findComment.isEmpty()){
            throw new EntityNotFoundException("덧글이 존재하지 않습니다.");
        }
        //유저 id가 서로 다른 경우
        if(!ContextHolder.getAccountId()
                .equals(findComment.get().getAccount().getAccountId())){
            throw new AccessDeniedException("부적절한 접근입니다.");
        }

        //Comment의 좋아요 부터 찾기.
        List<CommentLike> findCommentLike = findComment.get().getCommentLikes();

        //좋아요는 삭제.
        commentLikeRepository.deleteAll(findCommentLike);
        commentLikeRepository.flush();

        //덧글은 대댓글을 위한 삭제 조치.
        findComment.get().delete();
        commentRepository.save(findComment.get());
    }
}
