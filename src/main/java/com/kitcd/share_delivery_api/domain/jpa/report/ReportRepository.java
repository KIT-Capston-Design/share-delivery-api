package com.kitcd.share_delivery_api.domain.jpa.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {

	@Query("select count(r) from Report r where r.reportedAccount.accountId = :accountId")
	Long countByAccountId(Long accountId);

}
