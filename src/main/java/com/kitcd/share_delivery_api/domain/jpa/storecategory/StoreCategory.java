package com.kitcd.share_delivery_api.domain.jpa.storecategory;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
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
@Table(name = "STORE_CATEGORY")
public class StoreCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "STORE_CATEGORY_ID", nullable = false)
   private Long storeCategoryId;

   @OneToOne
   @JoinColumn(name = "IMAGE_FILE_ID")
   private ImageFile imageFile;

   @Column(name = "CATEGORY_NAME", nullable = false)
   private String categoryName;


}
