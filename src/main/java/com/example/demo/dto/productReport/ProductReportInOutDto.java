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
public class ProductReportInOutDto implements AbstractRequest {

    private Long count;

    private ActionType actionType;

    private Long productId;

    private Long userId;
}
