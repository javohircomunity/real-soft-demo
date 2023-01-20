package com.example.demo.dto.warehouse;

import com.example.demo.dto.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseModifyDto implements AbstractRequest {

    private Long id;

    private String name;

    private Long organizationId;
}
