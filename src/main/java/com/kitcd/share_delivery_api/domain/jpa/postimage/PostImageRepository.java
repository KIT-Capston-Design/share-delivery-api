package com.kitcd.share_delivery_api.domain.jpa.postimage;

import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    PostImage findPostImageByImageFile(ImageFile imageFile);
}
