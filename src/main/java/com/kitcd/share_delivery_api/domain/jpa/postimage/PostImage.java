package com.kitcd.share_delivery_api.domain.jpa.postimage;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "POST_IMAGE")
public class PostImage extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_IMAGE_ID", nullable = false)
   private Long postImageId;

   @ManyToOne
   @JoinColumn(name = "POST_ID", nullable = false)
   private Post post;

   @OneToOne
   @JoinColumn(name = "IMAGE_FILE_ID", nullable = false)
   private ImageFile imageFile;


}
