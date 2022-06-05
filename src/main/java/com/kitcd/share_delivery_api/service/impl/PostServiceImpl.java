package com.kitcd.share_delivery_api.service.impl;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShareRepository;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategoryRepository;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImage;
import com.kitcd.share_delivery_api.domain.jpa.postlike.PostLikeRepository;
import com.kitcd.share_delivery_api.dto.post.PostListDTO;
import com.kitcd.share_delivery_api.dto.post.UpdatePostDTO;
import com.kitcd.share_delivery_api.dto.post.WritePostRequestDTO;
import com.kitcd.share_delivery_api.dto.post.PostDTO;
import com.kitcd.share_delivery_api.service.PostImageService;
import com.kitcd.share_delivery_api.service.PostLikeService;
import com.kitcd.share_delivery_api.service.PostService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.GeometriesFactory;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                postImageService.saveAll(images, post);
            } catch (FileUploadException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        if(null != dto.getPlaceShare()) {
            placeShareRepository.save(dto.getPlaceShare().toEntity(post, findAddressWithLocation));
        }
        Boolean isLiked = postLikeService.isPostLiked(ContextHolder.getAccountId(), post.getPostId());
        return post.toDTO(isLiked);
    }

    @Override
    public List<PostListDTO> getPostLists(Double latitude, Double longitude, Long radius, LocalDateTime lastCreatedDateTime) {
        Location location = new Location(latitude, longitude);

        return postRepository.findPostListDTOWithLocationAndPagingWithLastCreatedDateTime(location, radius, lastCreatedDateTime);
    }

    @Override
    public PostDTO getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty()){
            return null;
        }

        post.get().increaseViewCount();
        postRepository.save(post.get());
        Boolean isLiked = postLikeService.isPostLiked(ContextHolder.getAccountId(), post.get().getPostId());

        return post.get().toDTO(isLiked);
    }

    @Override
    public List<PostListDTO> getPostListsWithCategoryFilter(Double latitude, Double longitude, Long radius, LocalDateTime lastCreatedDateTime, String categoryName) {
        Location location = new Location(latitude, longitude);

        return postRepository.findPostListDTOWithLocationAndPagingWithLastCreatedDateTimeAndFiltWithCategoryName(location, radius, lastCreatedDateTime, categoryName);
    }

    @Override
    public PostDTO updatePost(UpdatePostDTO dto, List<MultipartFile> images, Long postId) {
        Optional<Post> postTarget = postRepository.findById(postId);

        //다른 아이디가 들어오면 예외
        if(postTarget.isEmpty()){
            throw new EntityNotFoundException("서버에 저장되어있지 않은 post 입니다.");
        }

        //위치 찾고
        Optional<PlaceShare> placeShareTarget = placeShareRepository.findById(dto.getSharePlace().getPlaceShareId());

        //get 때기 위해
        Post postTemp = postTarget.get();
        PlaceShare placeShareTemp = placeShareTarget.orElse(null);

        //연관관계를 위해 필요한 Entity 찾기
        PostCategory postCategory = postCategoryRepository.findByCategoryName(dto.getCategory());
        String postCity = findAddressWithLocation.getCity(dto.getCoordinate());
        String sharedPlaceAddress = findAddressWithLocation.coordToAddr(new Location(dto.getSharePlace().getLatitude(), dto.getSharePlace().getLongitude()));

        //만약 null이 아닌 아이디라면..( null 일수도 있음. )
        if(placeShareTemp != null) {
            //만약 다른 위치거나 다른 content 라면...!
            if(!placeShareTemp.getCoordinate().equals(dto.getCoordinate()) || !placeShareTemp.getContent().equals(dto.getSharePlace().getDescription())){
                placeShareTemp = placeShareTemp.updatePlaceShare(dto.getSharePlace(), sharedPlaceAddress);
                placeShareTemp = placeShareRepository.save(placeShareTemp);
            }

        }

        // 만약 위치 데이터가 다르다면..!
        if(!postTemp.getCoordinate().equals(dto.getCoordinate())) {
            Point point = GeometriesFactory.createPoint(dto.getCoordinate().getLatitude(), dto.getCoordinate().getLongitude());
            postTemp = postTemp.updatePost(dto, postCategory, placeShareTemp, point, postCity);
        }

        postTemp = postRepository.save(postTemp);

        Boolean isLiked = postLikeService.isPostLiked(ContextHolder.getAccountId(), postId);

        //postImage entity 및 파일 삭제
        if(dto.getDeletedImages() != null) {
            //파일
            List<String> fileNames = dto.getDeletedImages().stream().map(ImageFile::getFileName).collect(Collectors.toList());

            fileNames.forEach(postImageService::delete);
        }

        //이미지가 null이 아니면
        if(images != null){
            try {
                postImageService.saveAll(images, postTemp);
            } catch (FileUploadException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return postTemp.toDTO(isLiked);
    }
}
