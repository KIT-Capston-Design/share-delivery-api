package com.kitcd.share_delivery_api.domain.postImage;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "POST_IMAGE")
public class PostImage extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_IMAGE_ID")
   private Long postImageId;

   @Column(name = "POST_ID")
   private Long postId;

   @Column(name = "IMAGE_FILE_ID")
   private Long imageFileId;


}
