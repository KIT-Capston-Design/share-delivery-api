package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.domain.jpa.report.ReportRepository;
import com.kitcd.share_delivery_api.domain.jpa.reportcategory.ReportCategory;
import com.kitcd.share_delivery_api.domain.jpa.reportcategory.ReportCategoryRepository;
import com.kitcd.share_delivery_api.dto.report.ReportRequestDTO;
import com.kitcd.share_delivery_api.service.ReportService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;
	private final ReportCategoryRepository reportCategoryRepository;
	private final AccountRepository accountRepository;

	@Override
	public void reportAccount(ReportRequestDTO dto) {

		// 유저 정보 가져오기 (신고자, 신고대상)
		Account reporter = ContextHolder.getAccount();
		Account reportedAccount = accountRepository.findByAccountId(dto.getReportedAccountId());

		if (reporter == null || reportedAccount == null) {
			throw new EntityNotFoundException(Account.class + "Entity is Not Found");
		}

		// ReportCategory 가져오기
		ReportCategory reportCategory = reportCategoryRepository.findByCategoryName(dto.getReportCategory());

		if (reportCategory == null) {
			throw new EntityNotFoundException(ReportCategory.class + "Entity is Not Found with " + dto.getReportCategory());
		}


		// Report 생성하기
		reportRepository.save(dto.toEntity(reporter, reportedAccount, reportCategory, dto));


		// NOTE : 원래라면 항목에 따라 가산점을 계산하는 코드가 필요하지만, 시간 관계상 랜덤으로 -1.0 ~ -0.6 로 주겠습니다.
		Double point = -Math.round((Math.random() * 0.4 + 0.6) * 10) / 10.0;

		// NOTE : 신고 횟수 카운팅을 해서 몇회 이상이면 3일/7일/영구 정지 등의 처벌 가능
		Long count = reportRepository.countByAccountId(reportedAccount.getAccountId());


		// 신고받은 유저의 매너 점수 조정
		reportedAccount.updateMannerScore(point);
		accountRepository.save(reportedAccount);

	}

}
