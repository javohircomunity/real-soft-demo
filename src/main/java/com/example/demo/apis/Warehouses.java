package com.example.demo.apis;

import com.example.demo.domain.Warehouse;
import com.example.demo.dto.pagination.WarehouseSearch;
import com.example.demo.dto.warehouse.WarehouseModifyDto;
import org.springframework.data.domain.Page;

public interface Warehouses {

    void modify(WarehouseModifyDto modifyDto);

    Warehouse get(Long id);

    Page<Warehouse> search(WarehouseSearch search);

    void delete(Long id);
}
