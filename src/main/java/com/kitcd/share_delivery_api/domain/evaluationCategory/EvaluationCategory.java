package com.kitcd.share_delivery_api.domain.evaluationCategory;

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
@Table(name = "EVALUATION_CATEGORY")
public class EvaluationCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "EVALUATION_CATEGORY_ID")
   private Long evaluationCategoryId;

   @Column(name = "CATEGORY_NAME")
   private String categoryName;

   @Column(name = "VALUE")
   private Long value;


}
