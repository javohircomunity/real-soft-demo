package com.example.demo.apis;

import com.example.demo.domain.ProductReport;
import com.example.demo.dto.pagination.ProductReportSearch;
import com.example.demo.dto.productReport.ProductReportInOutDto;
import org.springframework.data.domain.Page;

public interface ProductReports {

    void productIn(ProductReportInOutDto inOutDto);

    void productOut(ProductReportInOutDto inOutDto);

    ProductReport get(Long id);

    Page<ProductReport> search(ProductReportSearch search);

}
