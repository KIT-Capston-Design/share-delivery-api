package com.kitcd.share_delivery_api.controller.comment;


import com.kitcd.share_delivery_api.dto.comment.CommentDTO;
import com.kitcd.share_delivery_api.dto.comment.CommentWriteDTO;
import com.kitcd.share_delivery_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> writeComment(@RequestBody CommentWriteDTO dto){
        CommentDTO comment = commentService.writeComment(dto);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId){
        List<CommentDTO> list = commentService.getCommentsByPostId(postId);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody String content){
        CommentDTO comment = commentService.updateComment(commentId, content);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("Delete Success");
    }
}
