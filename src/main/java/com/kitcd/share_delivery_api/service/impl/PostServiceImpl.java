package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostResponseDTO;
import com.kitcd.share_delivery_api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Override
    public WritePostResponseDTO writePost(WritePostRequestDTO dto) {

        return null;
    }
}
