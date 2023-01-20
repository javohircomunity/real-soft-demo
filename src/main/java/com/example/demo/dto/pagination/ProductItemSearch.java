package com.example.demo.dto.pagination;

import com.example.demo.dto.AbstractRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductItemSearch extends PageableSearch implements AbstractRequest {

    private Long organizationId;

    private Long productId;
}
