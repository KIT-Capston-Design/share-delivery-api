package com.kitcd.share_delivery_api.domain.userEvaluation;

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
@Table(name = "USER_EVALUATION")
public class UserEvaluation extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "USER_EVALUATION_ID")
   private Long userEvaluationId;

   @Column(name = "TARGET_USER_ID")
   private Long targetUserId;

   @Column(name = "EVALUATOR_ID")
   private Long evaluatorId;

   @Column(name = "EVALUATION_CATEGORY_ID")
   private Long evaluationCategoryId;


}
