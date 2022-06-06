package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.dto.comment.CommentDTO;
import com.kitcd.share_delivery_api.dto.comment.CommentWriteDTO;

import java.util.List;

public interface CommentService {

    CommentDTO writeComment(CommentWriteDTO dto);

    Comment getComment(Long id);

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO updateComment(Long commentId, String content);

    void deleteComment(Long commentId);
}
