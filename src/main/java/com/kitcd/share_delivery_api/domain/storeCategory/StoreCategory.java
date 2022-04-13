package com.kitcd.share_delivery_api.domain.storeCategory;

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
@Table(name = "STORE_CATEGORY")
public class StoreCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "STORE_CATEGORY_ID")
   private Long storeCategoryId;

   @Column(name = "PARENT_CATEGORY_ID")
   private Long parentCategoryId;

   @Column(name = "IMAGE_FILE_ID")
   private Long imageFileId;

   @Column(name = "CATEGORY_NAME")
   private String categoryName;

   @Column(name = "LEVEL")
   private Long level;


}
