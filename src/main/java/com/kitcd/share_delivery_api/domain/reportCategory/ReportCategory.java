package com.kitcd.share_delivery_api.domain.reportCategory;

import com.kitcd.share_delivery_api.domain.common.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "REPORT_CATEGORY")
public class ReportCategory extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "REPORT_CATEGORY_ID")
   private Long reportCategoryId;

   @Column(name = "PARENT_CATEGORY_ID")
   private Long parentCategoryId;

   @Column(name = "CATEGORY_NAME")
   private String categoryName;

   @Column(name = "LEVEL")
   private Long level;


}
