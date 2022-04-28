package com.kitcd.share_delivery_api.domain.jpa.postCategory;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "POST_CATEGORY")
public class PostCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "POST_CATEGORY_ID", nullable = false)
   private Long postCategoryId;

   @Column(name = "CATEGORY_NAME", nullable = false)
   private String categoryName;

   @OneToMany(mappedBy = "postCategory")
   private List<Post> posts = new LinkedList<>();

}
