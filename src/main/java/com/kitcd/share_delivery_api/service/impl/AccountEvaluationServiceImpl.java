package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.domain.jpa.accountevaluation.AccountEvaluation;
import com.kitcd.share_delivery_api.domain.jpa.accountevaluation.AccountEvaluationRepository;
import com.kitcd.share_delivery_api.domain.jpa.evaluationcategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.jpa.evaluationcategory.EvaluationCategoryRepository;
import com.kitcd.share_delivery_api.dto.accountevaluation.AccountEvaluationRequestDTO;
import com.kitcd.share_delivery_api.service.AccountEvaluationService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountEvaluationServiceImpl  implements AccountEvaluationService {

	private static final double defaultMannerScore = 36.5;

	private final AccountEvaluationRepository accountEvaluationRepository;
	private final AccountRepository accountRepository;
	private final EvaluationCategoryRepository evaluationCategoryRepository;

	@Override
	public void evaluateAccount(AccountEvaluationRequestDTO dto) {

		// 유저 정보 가져오기 (평가자, 평가대상)
		Account evaluator = ContextHolder.getAccount();
		Account targetAccount = accountRepository.findByAccountId(dto.getTargetAccountId());

		if (evaluator == null || targetAccount == null) {
			throw new EntityNotFoundException(Account.class + "Entity is Not Found");
		}

		// EvaluationCategory 가져오기
		EvaluationCategory evaluationCategory = evaluationCategoryRepository.findByCategoryName(dto.getCategoryName());

		if (evaluationCategory == null) {
			throw new EntityNotFoundException(EvaluationCategory.class + "Entity is Not Found with " + dto.getCategoryName());
		}


		// AccountEvaluation 생성하기
		accountEvaluationRepository.save(dto.toEntity(targetAccount, evaluator, evaluationCategory));


		/** NOTE
		 * 원래라면 항목에 따라 가산점을 계산하는 코드가 필요하지만, 시간 관계상 랜덤으로 0.3 ~ 0.7 로 주겠습니다.
		 * 단, 사용자의 매너 점수가 양극단에 가까워질수록 점수 변화폭이 줄어듭니다.
		 * evaluationCategory.getValue() == 1L	=> 긍정적인 평가, 매너 점수 상승
		 * evaluationCategory.getValue() == -1L	=> 부정적인 평가, 매너 점수 하강
		 */
		double basePoint = (Math.random() * 0.4 + 0.3) * evaluationCategory.getValue();
		double ratio =  defaultMannerScore / (defaultMannerScore + Math.abs(targetAccount.getMannerScore() - defaultMannerScore));
		Double point = Math.round(basePoint * ratio * 10) / 10.0;


		// 평가받은 유저의 매너 점수 조정
		targetAccount.updateMannerScore(point);
		accountRepository.save(targetAccount);

	}

}
