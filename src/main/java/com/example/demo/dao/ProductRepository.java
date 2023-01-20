package com.example.demo.dao;

import com.example.demo.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends SoftDeleteJpaRepository<Product> {
}
