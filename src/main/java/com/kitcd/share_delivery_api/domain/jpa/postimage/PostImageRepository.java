package com.kitcd.share_delivery_api.domain.jpa.postimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    @Query("SELECT pi FROM PostImage pi JOIN ImageFile if ON pi.imageFile.fileName = if.fileName WHERE if.fileName = :fileName")
    PostImage getPostImagesWithFileName(String filename);
}
