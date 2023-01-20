package com.example.demo.dao;

import com.example.demo.domain.ProductItem;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends SoftDeleteJpaRepository<ProductItem> {
}
