package com.example.demo.dto.productReport;

import com.example.demo.dto.AbstractRequest;
import com.example.demo.dto.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReportDto implements AbstractRequest {

    protected Long id;

    protected ActionType actionType;

    protected Long count;

    protected String localUser;

    protected String productName;

    protected String organizationName;
}
