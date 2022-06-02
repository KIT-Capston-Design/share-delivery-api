package com.kitcd.share_delivery_api.controller.post;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.PostDTO;
import com.kitcd.share_delivery_api.service.PostImageService;
import com.kitcd.share_delivery_api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;



    @PostMapping("")                //multipart로 데이터를 받아옴.                  //null 일 수 있음.
    public ResponseEntity<?> writePost(@RequestPart(value = "post") WritePostRequestDTO dto, @RequestParam(value = "postImages",required = false) List<MultipartFile> images){

        PostDTO post = postService.writePost(dto, images);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
