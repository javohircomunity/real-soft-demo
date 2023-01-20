package com.example.demo.dao;

import com.example.demo.domain.Organization;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRepository extends SoftDeleteJpaRepository<ProductItem> {

    Optional<ProductItem> findByProduct(Product product);

    List<ProductItem> findAllByOrganization(Organization organization);
}
