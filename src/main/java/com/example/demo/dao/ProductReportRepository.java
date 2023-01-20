package com.example.demo.dao;

import com.example.demo.domain.ProductReport;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReportRepository extends SoftDeleteJpaRepository<ProductReport> {
}
