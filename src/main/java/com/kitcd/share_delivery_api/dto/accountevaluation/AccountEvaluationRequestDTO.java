package com.kitcd.share_delivery_api.dto.accountevaluation;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.accountevaluation.AccountEvaluation;
import com.kitcd.share_delivery_api.domain.jpa.evaluationcategory.EvaluationCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEvaluationRequestDTO {

	@NotBlank
	private Long targetAccountId;
	@NotBlank
	private String categoryName;

	public AccountEvaluation toEntity(Account targetAccount, Account evaluator, EvaluationCategory evaluationCategory) {
		return AccountEvaluation.builder()
				.targetAccount(targetAccount)
				.evaluator(evaluator)
				.evaluationCategory(evaluationCategory)
				.build();
	}

}
