package com.kitcd.share_delivery_api.domain.jpa.imagefile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    ImageFile getImageFileByFileName(String fileName);
}
