package com.example.demo.dto.productItem;

import com.example.demo.dto.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDto implements AbstractRequest {

    protected Long id;

    protected Long count;

    protected String name;

    protected String organizationName;
}
