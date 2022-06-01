package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDTO writePost(WritePostRequestDTO dto, List<MultipartFile> images);
}
