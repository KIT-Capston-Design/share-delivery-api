package com.kitcd.share_delivery_api.domain.jpa.reportcategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCategoryRepository extends JpaRepository<ReportCategory, Long> {
	ReportCategory findByCategoryName(String categoryName);
}
