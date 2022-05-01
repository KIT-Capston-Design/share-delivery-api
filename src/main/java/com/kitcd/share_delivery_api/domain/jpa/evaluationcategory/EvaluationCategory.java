package com.kitcd.share_delivery_api.domain.jpa.evaluationcategory;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.accountevaluation.AccountEvaluation;
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
@Table(name = "EVALUATION_CATEGORY")
public class EvaluationCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "EVALUATION_CATEGORY_ID", nullable = false)
   private Long evaluationCategoryId;

   @Column(name = "CATEGORY_NAME", nullable = false)
   private String categoryName;

   @Column(name = "VALUE", nullable = false)
   private Long value;

   @OneToMany(mappedBy = "evaluationCategory")
   private List<AccountEvaluation> evaluations = new LinkedList<>();

}
