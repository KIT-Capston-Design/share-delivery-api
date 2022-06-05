package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.post.PostListDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

    PostDTO writePost(WritePostRequestDTO dto, List<MultipartFile> images);

    List<PostListDTO> getPostLists(Double latitude, Double longitude, Long radius, LocalDateTime lastCreatedDateTime);

    PostDTO getPost(Long postId);

    List<PostListDTO> getPostListsWithCategoryFilter(Double latitude, Double longitude, Long radius, LocalDateTime lastCreatedDateTime, String categoryName);
}
