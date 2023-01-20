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
public class ProductSearch extends PageableSearch implements AbstractRequest {

    private String name;

    private Long organizationId;

}
