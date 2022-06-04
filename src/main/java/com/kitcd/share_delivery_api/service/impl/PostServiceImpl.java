package com.kitcd.share_delivery_api.service.impl;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShareRepository;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategoryRepository;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLikeRepository;
import com.kitcd.share_delivery_api.dto.post.PostListDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.PostDTO;
import com.kitcd.share_delivery_api.service.PostImageService;
import com.kitcd.share_delivery_api.service.PostLikeService;
import com.kitcd.share_delivery_api.service.PostService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PlaceShareRepository placeShareRepository;
    private final FindAddressWithLocation findAddressWithLocation;

    private final PostImageService postImageService;

    private final PostLikeService postLikeService;

    @Override
    public PostDTO writePost(WritePostRequestDTO dto, List<MultipartFile> images) {


        PostCategory category = postCategoryRepository.findByCategoryName(dto.getCategory());

        String address = findAddressWithLocation.getCity(dto.getCoordinate());
        Post post = postRepository.save(dto.toEntity(category, address));

        if(images != null) {
            try {
                postImageService.saveAll(images, post.getPostId());
            } catch (FileUploadException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        if(null != dto.getSharePlace()) {
            placeShareRepository.save(dto.getSharePlace().toEntity(post, findAddressWithLocation));
        }
        Boolean isLiked = postLikeService.isPostLiked(ContextHolder.getAccountId(), post.getPostId());
        return post.toDTO(isLiked);
    }

    @Override
    public List<PostListDTO> getPostLists(Double latitude, Double longitude, Long radius, LocalDateTime lastCreatedDateTime) {
        Location location = new Location(latitude, longitude);

        return postRepository.findPostListDTOWithLocationAndPagingWithLastCreatedDateTime(location, radius, lastCreatedDateTime);
    }
}
