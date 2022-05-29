package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostResponseDTO;

public interface PostService {

    WritePostResponseDTO writePost(WritePostRequestDTO dto);
}
