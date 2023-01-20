package com.example.demo.dto.product;

import com.example.demo.dto.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements AbstractRequest {

    protected Long id;

    protected String name;

    protected String organizationName;
}
