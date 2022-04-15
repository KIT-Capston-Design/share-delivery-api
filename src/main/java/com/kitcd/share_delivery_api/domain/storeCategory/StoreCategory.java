package com.kitcd.share_delivery_api.domain.storeCategory;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.deliveryRoom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.evaluationCategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.imageFile.ImageFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
   @JoinColumn(name = "PARENT_CATEGORY_ID")
   private StoreCategory parentCategory;

   @OneToOne
   @JoinColumn(name = "IMAGE_FILE_ID", nullable = false)
   private ImageFile imageFile;

   @Column(name = "CATEGORY_NAME", nullable = false)
   private String categoryName;

   @Column(name = "LEVEL", nullable = false)
   private Long level;


   @OneToMany(mappedBy = "storeCategory")
   private List<DeliveryRoom> deliveryRooms = new LinkedList<>();

}
