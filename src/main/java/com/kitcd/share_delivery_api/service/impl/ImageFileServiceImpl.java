package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFileRepository;
import com.kitcd.share_delivery_api.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
@Transactional
public class ImageFileServiceImpl implements ImageFileService {

    @Value("${web.static.base-dir}")
    private String BASE_DIR;
    private static final String IMAGE_DIR = "images/";

    private final ImageFileRepository imageFileRepository;

    @Override
    public ImageFile save(MultipartFile file) {

        if(file == null || file.isEmpty()) return null;

        String newFileName = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = Objects.requireNonNull(originalFileName).substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String fileFullPath = BASE_DIR + IMAGE_DIR + newFileName + "." + fileExtension;

        //새로 저장될 경로의 파일 객체
        File newFile = new File(fileFullPath);

        //극악의 중복 발생 시 파일 이름에 숫자 추가
        for(int i = 0; newFile.exists(); i++){
            newFileName += Integer.toString(i);
            fileFullPath = BASE_DIR + IMAGE_DIR + newFileName + "." + fileExtension;
            newFile = new File(fileFullPath);
        }

        try{
            file.transferTo(newFile);
        }catch (IOException e){
            //unchecked exception 발생시킬 필요 있기에.
            throw new IllegalStateException("Failed to save imagefile");
        }

        ImageFile imageFile = ImageFile.builder()
                .dirPath(IMAGE_DIR)
                .fileName(newFileName)
                .originalFileName(originalFileName)
                .fileExtension(fileExtension)
                .fileSize(file.getSize()/(double)1000) //kb로 치환
                .build();

        return imageFileRepository.save(imageFile);
    }

    @Override
    public List<ImageFile> saveAll(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public void delete(String filePath) throws FileSystemException {
        File file = new File(BASE_DIR + filePath);
        log.error(BASE_DIR + filePath);
        if(!file.exists()) throw new IllegalStateException("해당 파일이 존재하지 않습니다.");

        if(!file.delete()) throw new FileSystemException("파일을 삭제할 수 없습니다.");
    }
}
