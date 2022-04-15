package com.kitcd.share_delivery_api.domain.postImage;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.imageFile.ImageFile;
import com.kitcd.share_delivery_api.domain.post.Post;
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
   @Column(name = "POST_IMAGE_ID", nullable = false)
   private Long postImageId;

   @Column(name = "POST_ID", nullable = false)
   private Post post;

   @Column(name = "IMAGE_FILE_ID", nullable = false)
   private ImageFile imageFile;


}
