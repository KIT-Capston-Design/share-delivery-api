package com.kitcd.share_delivery_api.controller.post;

import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostResponseDTO;
import com.kitcd.share_delivery_api.service.ImageFileService;
import com.kitcd.share_delivery_api.service.PostImageService;
import com.kitcd.share_delivery_api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/api/posts")
@Transactional  //파일이 저장 및 수정 안되면 다 롤백하도록..
public class PostController {


    private final PostService postService;

    private final PostImageService postImageService;


    @PostMapping("")                //multipart로 데이터를 받아옴.                  //null 일 수 있음.
    public ResponseEntity<?> writePost(@RequestPart(value = "dto") WritePostRequestDTO dto, @RequestParam(value = "images",required = false) List<MultipartFile> images){
        WritePostResponseDTO post = postService.writePost(dto);

        if(images != null) {
            try {
                postImageService.saveAll(images, post.getPostId());
            } catch (FileUploadException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getClass() + ":" + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
