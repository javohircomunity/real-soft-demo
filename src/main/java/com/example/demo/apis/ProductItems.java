package com.example.demo.apis;

import com.example.demo.domain.ProductItem;
import com.example.demo.dto.pagination.ProductItemSearch;
import org.springframework.data.domain.Page;

public interface ProductItems {

    Page<ProductItem> search(ProductItemSearch search);
}
