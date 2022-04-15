package com.kitcd.share_delivery_api.domain.userEvaluation;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.evaluationCategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.user.User;
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
   @Column(name = "USER_EVALUATION_ID", nullable = false)
   private Long userEvaluationId;

   @ManyToOne
   @JoinColumn(name = "TARGET_USER_ID", nullable = false)
   private User targetUser;

   @ManyToOne
   @JoinColumn(name = "EVALUATOR_ID", nullable = false)
   private User evaluator;

   @ManyToOne
   @JoinColumn(name = "EVALUATION_CATEGORY_ID", nullable = false)
   private EvaluationCategory evaluationCategory;


}
