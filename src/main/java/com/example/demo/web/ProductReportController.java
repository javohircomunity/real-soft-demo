package com.example.demo.web;

import com.example.demo.apis.ProductReports;
import com.example.demo.domain.ProductReport;
import com.example.demo.dto.pagination.ProductReportSearch;
import com.example.demo.dto.productReport.ProductReportDto;
import com.example.demo.dto.productReport.ProductReportInOutDto;
import com.example.demo.mapping.ProductReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/product-reports")
public class ProductReportController {

    private final ProductReports productReports;
    private final ProductReportMapper productReportMapper;

    @Autowired
    public ProductReportController(ProductReports productReports, ProductReportMapper productReportMapper) {
        this.productReports = productReports;
        this.productReportMapper = productReportMapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PostMapping(value = "search", produces = "application/json")
    public Page<ProductReportDto> search(@RequestBody ProductReportSearch search) {
        log.trace("Accessing POST api/product-reports/search/{}", search);

        Page<ProductReport> foundReports = productReports.search(search);

        log.trace("{} product-reports found and {} returned to front-end", foundReports.getTotalElements(), foundReports.getSize());

        return foundReports.map(productReportMapper::toDto);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @GetMapping(value = "get/{productReportId}", produces = "application/json")
    public ProductReportDto get(@PathVariable Long productReportId) {
        log.trace("Accessing GET api/product-reports/get/{}", productReportId);

        ProductReport productReport = productReports.get(productReportId);

        log.trace("{} get product-report and returned to front-end", productReport);

        return productReportMapper.toDto(productReport);
    }

    @Transactional
    @PostMapping(value = "product-in", produces = "application/json")
    public void productIn(@RequestBody ProductReportInOutDto dto) {
        log.trace("Accessing POST api/product-reports/product-in/{} ", dto.toJsonString());

        productReports.productIn(dto);

        log.trace("Product has entered successfully");
    }

    @Transactional
    @PostMapping(value = "product-out", produces = "application/json")
    public void productOut(@RequestBody ProductReportInOutDto dto) {
        log.trace("Accessing POST api/product-reports/product-out/{} ", dto.toJsonString());

        productReports.productOut(dto);

        log.trace("Product has left out successfully");
    }
}
