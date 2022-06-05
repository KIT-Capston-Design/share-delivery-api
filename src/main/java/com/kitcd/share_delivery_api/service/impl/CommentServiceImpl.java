package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.comment.CommentRepository;
import com.kitcd.share_delivery_api.domain.jpa.commentlike.CommentLikeRepository;
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

    @Override
    public CommentDTO writeComment(CommentWriteDTO dto) {
        Comment parent = null;


        //fcm title, body, data 초기화
        Long fcmTargetId;
        String title = "내가 작성한 글에 댓글이 달렸어요!";
        HashMap<String, Object> data = new HashMap<>();
        data.put("postId", dto.getPostId());
        data.put("type", FCMDataType.POST_COMMENT);
        data.put("parentId", null);

        //대댓글일 경우
        if(dto.getParentId()!=null){
            parent = getComment(dto.getParentId());
            fcmTargetId = parent.getAccount().getAccountId();
            title = "내가 작성한 댓글에 대댓글이 달렸어요!";
            data.replace("type", FCMDataType.COMMENT_PLUS);  //덮어쓰기
            data.replace("parentId", dto.getParentId());
        }
        Optional<Post> findPost = postRepository.findById(dto.getPostId());

        if(findPost.isEmpty()){
            throw new EntityNotFoundException("해당 post를 찾을 수 없습니다.");
        }

        //그냥 댓글일 경우 저장..!
        Comment comment = commentRepository.save(dto.toEntity(parent, findPost.get()));


        //글작성자에게 전송.
        fcmTargetId = comment.getPost().getAccount().getAccountId();


        firebaseCloudMessageService.sendMessageTo(
                loggedOnInformationService.getFcmTokenByAccountId(fcmTargetId),
                title, comment.getContent(),data);

        return comment.toDTO(false); //처음은 무조건 false.
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
}
