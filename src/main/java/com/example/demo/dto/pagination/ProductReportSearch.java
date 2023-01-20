package com.example.demo.dto.pagination;

import com.example.demo.dto.AbstractRequest;
import com.example.demo.dto.enums.ActionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductReportSearch extends PageableSearch implements AbstractRequest {

    private Long organizationId;

    private ActionType actionType;

    private Long productId;

    private Long userId;
}
