package com.kitcd.share_delivery_api.domain.jpa.imagefile;


import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "IMAGE_FILE")
@Slf4j
public class ImageFile extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "IMAGE_FILE_ID", nullable = false)
   private Long imageFileId;

   @Column(name = "ORIGINAL_FILE_NAME", nullable = false)
   private String originalFileName;

   @Column(name = "FILE_NAME", nullable = false)
   private String fileName;

   @Column(name = "FILE_EXTENSION", nullable = false)
   private String fileExtension;

   @Column(name = "DIR_PATH", nullable = false)
   private String dirPath;

   @Column(name = "FILE_SIZE", nullable = false)
   private Double fileSize;

   public String extractUrl(){
      return String.format("/%s%s.%s", dirPath, fileName, fileExtension);
   }
   public static String getFileName(String extractUrl){
      String filename = null;

      String[] arr = extractUrl.replaceFirst("/", "").split("/");

      String[] fileNamePlusExtension = arr[1].split("[.]");

      filename = fileNamePlusExtension[0];

      return filename;
   }
}
