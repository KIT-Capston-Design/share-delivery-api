package com.kitcd.share_delivery_api.service.impl;


import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImage;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImageRepository;
import com.kitcd.share_delivery_api.service.ImageFileService;
import com.kitcd.share_delivery_api.service.PostImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;
    private final ImageFileService imageFileService;

    private final PostRepository postRepository;

    @Override
    public List<PostImage> saveAll(List<MultipartFile> imageFiles, Post post) throws FileUploadException {
        List<ImageFile> savedFiles = imageFileService.saveAll(imageFiles);


        List<PostImage> postImages = savedFiles.stream().map(i -> postImageRepository.save(PostImage.builder()
                        .post(post)
                        .imageFile(i)
                        .build()))
                .collect(Collectors.toList());
        return postImages;
    }

    @Override
    public PostImage save(MultipartFile imageFile, Post post) throws FileUploadException {
        ImageFile savedImage = imageFileService.save(imageFile);

        return postImageRepository.save(PostImage.builder()
                .post(post)
                .imageFile(savedImage)
                .build());
    }
}
