package com.example.demo.mapping;

import com.example.demo.domain.ProductReport;
import com.example.demo.dto.productReport.ProductReportDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ProductReportMapper {

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "organization.name", target = "organizationName")
    public abstract ProductReportDto toDto(ProductReport productReport);

    @AfterMapping
    public void afterMapping(ProductReport productReport, @MappingTarget ProductReportDto productReportDto) {
        productReportDto.setLocalUser(productReport.getLocalUser().getFirstName() + " " + productReport.getLocalUser());
    }
}
