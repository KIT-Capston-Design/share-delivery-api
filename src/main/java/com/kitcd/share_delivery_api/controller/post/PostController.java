package com.kitcd.share_delivery_api.controller.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.PostDTO;
import com.kitcd.share_delivery_api.service.PostImageService;
import com.kitcd.share_delivery_api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;



    @PostMapping("")                //multipart로 데이터를 받아옴.                  //null 일 수 있음.
    public ResponseEntity<?> writePost(@RequestParam(value = "post") String postDetails, @RequestParam(value = "postImages",required = false) List<MultipartFile> images){
        ObjectMapper om = new ObjectMapper();

        WritePostRequestDTO dto = null;
        try {
            dto = om.readValue(postDetails, WritePostRequestDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        PostDTO post = postService.writePost(dto, images);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("")
    public ResponseEntity<?> getPosts(@RequestParam @NotNull Double latitude, @RequestParam @NotNull Double longitude, @RequestParam @NotNull Long radius, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedDateTime){

        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostLists(latitude, longitude, radius, lastCreatedDateTime));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId){
        PostDTO postDTO = postService.getPost(postId);

        if(postDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("postId가 적절하지 않습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }
}
