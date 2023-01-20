package com.example.demo.apis;

import com.example.demo.domain.Product;
import com.example.demo.dto.pagination.ProductSearch;
import com.example.demo.dto.product.ProductModifyDto;
import org.springframework.data.domain.Page;

public interface Products {

    void modify(ProductModifyDto modifyDto);

    Product get(Long id);

    Page<Product> search(ProductSearch search);

    void delete(Long id);
}
