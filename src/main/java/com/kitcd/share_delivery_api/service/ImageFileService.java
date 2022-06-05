package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystemException;
import java.util.List;

public interface ImageFileService {

    ImageFile save(MultipartFile multipartFile);

    /**
     파일명.확장자만 보내면 되도록.
     */
    void delete(String filePath) throws FileSystemException;

    List<ImageFile> saveAll(List<MultipartFile> multipartFiles);
}
