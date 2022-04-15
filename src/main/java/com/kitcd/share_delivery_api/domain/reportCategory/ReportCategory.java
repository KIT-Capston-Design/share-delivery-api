package com.kitcd.share_delivery_api.domain.reportCategory;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.evaluationCategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.report.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "REPORT_CATEGORY")
public class ReportCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "REPORT_CATEGORY_ID", nullable = false)
   private Long reportCategoryId;

   @OneToOne
   @JoinColumn(name = "PARENT_CATEGORY_ID")
   private ReportCategory parentCategory;

   @Column(name = "CATEGORY_NAME", nullable = false)
   private String categoryName;

   @Column(name = "LEVEL", nullable = false)
   private Long level;

   @OneToMany(mappedBy = "reportCategory")
   private List<Report> reports = new LinkedList<>();

}
