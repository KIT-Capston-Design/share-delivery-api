package com.kitcd.share_delivery_api.domain.accountEvaluation;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.evaluationCategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "ACCOUNT_EVALUATION")
public class AccountEvaluation extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ACCOUNT_EVALUATION_ID", nullable = false)
   private Long accountEvaluationId;

   @ManyToOne
   @JoinColumn(name = "TARGET_ACCOUNT_ID", nullable = false)
   private Account targetAccount;

   @ManyToOne
   @JoinColumn(name = "EVALUATOR_ID", nullable = false)
   private Account evaluator;

   @ManyToOne
   @JoinColumn(name = "EVALUATION_CATEGORY_ID", nullable = false)
   private EvaluationCategory evaluationCategory;


}
