package com.kitcd.share_delivery_api.controller.accountevaluation;


import com.kitcd.share_delivery_api.dto.accountevaluation.AccountEvaluationRequestDTO;
import com.kitcd.share_delivery_api.service.AccountEvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/evaluations")
public class AccountEvaluationController {

	private final AccountEvaluationService accountEvaluationService;

	@PostMapping("")
	public ResponseEntity<?> evaluateAccount(@RequestBody AccountEvaluationRequestDTO accountEvaluationRequestDTO){

		accountEvaluationService.evaluateAccount(accountEvaluationRequestDTO);

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
