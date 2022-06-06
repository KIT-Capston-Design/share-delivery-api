package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.accountevaluation.AccountEvaluationRequestDTO;

public interface AccountEvaluationService {

	void evaluateAccount(AccountEvaluationRequestDTO accountEvaluationRequestDTO);
}
