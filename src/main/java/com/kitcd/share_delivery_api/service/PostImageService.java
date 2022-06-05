package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImage;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostImageService {

    List<PostImage> saveAll(List<MultipartFile> imageFiles, Post post) throws FileUploadException;

    PostImage save(MultipartFile imageFile, Post post) throws FileUploadException;

}
