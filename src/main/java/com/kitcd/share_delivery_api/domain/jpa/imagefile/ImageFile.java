package com.kitcd.share_delivery_api.domain.jpa.imagefile;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "IMAGE_FILE")
public class ImageFile extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "IMAGE_FILE_ID", nullable = false)
   private Long imageFileId;

   @Column(name = "FILE_NAME", nullable = false)
   private String fileName;

   @Column(name = "FILE_EXTENSION", nullable = false)
   private String fileExtension;

   @Column(name = "FILE_PATH", nullable = false)
   private String filePath;

   @Column(name = "FILE_SIZE", nullable = false)
   private Double fileSize;


}
