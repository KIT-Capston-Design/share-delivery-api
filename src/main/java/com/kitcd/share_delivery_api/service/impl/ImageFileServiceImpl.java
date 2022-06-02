package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFileRepository;
import com.kitcd.share_delivery_api.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ImageFileServiceImpl implements ImageFileService {

    @Value("${file.dir}")
    private String imageFileSaveDir;

    private final ImageFileRepository imageFileRepository;

    @Override
    public ImageFile save(MultipartFile file) throws FileUploadException {

        String newFileName = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = Objects.requireNonNull(originalFileName).substring(file.getOriginalFilename().lastIndexOf(".")+1);

        try{
            file.transferTo(new File(imageFileSaveDir + newFileName));
        }catch (IOException e){
            throw new FileUploadException("File can not save");
        }

        ImageFile imageFile = ImageFile.builder()
                .dirPath(imageFileSaveDir)
                .fileName(newFileName)
                .originalFileName(originalFileName)
                .fileExtension(fileExtension)
                .fileSize(file.getSize()/(double)1000) //kb로 치환
                .build();

        return imageFileRepository.save(imageFile);
    }

    @Override
    public List<ImageFile> saveAll(List<MultipartFile> multipartFiles) {
        List<ImageFile> imageFiles = multipartFiles.stream().map(i -> {
                    try {
                        return save(i);
                    } catch (FileUploadException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).collect(Collectors.toList());
        return imageFiles;
    }


    @Override
    public void delete(String filePath) throws FileSystemException {
        File file = new File(filePath);
        if(!file.exists()) return;

        if(!file.delete()) throw new FileSystemException("파일에 오류가 있습니다.");
    }


}
