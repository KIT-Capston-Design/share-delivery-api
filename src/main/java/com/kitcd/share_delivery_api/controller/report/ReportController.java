package com.kitcd.share_delivery_api.controller.report;

import com.kitcd.share_delivery_api.dto.report.ReportRequestDTO;
import com.kitcd.share_delivery_api.service.ReportService;
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
@RequestMapping("/api/reports")
public class ReportController {

	private final ReportService reportService;

	@PostMapping("")
	public ResponseEntity<?> reportAccount(@RequestBody ReportRequestDTO reportRequestDTO) {

		reportService.reportAccount(reportRequestDTO);

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
