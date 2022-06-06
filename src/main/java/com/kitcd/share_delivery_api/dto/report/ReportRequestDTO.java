package com.kitcd.share_delivery_api.dto.report;


import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.report.Report;
import com.kitcd.share_delivery_api.domain.jpa.reportcategory.ReportCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequestDTO {

	@NotBlank
	private Long reportedAccountId;
	@NotBlank
	private String reportCategory;
	@NotBlank
	private String reportType;
	@NotBlank
	private String description;

	public Report toEntity(Account reporter, Account reportedAccount, ReportCategory reportCategory, ReportRequestDTO dto) {
		return Report.builder()
				.reporter(reporter)
				.reportedAccount(reportedAccount)
				.reportCategory(reportCategory)
				.description(dto.description)
				.build();
	}

}
