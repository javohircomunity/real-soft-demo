package com.example.demo.apis.impl;

import com.example.demo.apis.ProductItems;
import com.example.demo.dao.ProductItemRepository;
import com.example.demo.domain.ProductItem;
import com.example.demo.dto.pagination.ProductItemSearch;
import com.example.demo.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductItemBean implements ProductItems {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public ProductItemBean(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    @Override
    public Page<ProductItem> search(ProductItemSearch search) {
        return productItemRepository.findAll((Specification<ProductItem>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(search.getOrganizationId())) {
                predicates.add(cb.equal(root.get("organization").get("id"), search.getOrganizationId()));
            }

            if (!ObjectUtils.isEmpty(search.getProductId())) {
                predicates.add(cb.equal(root.get("product").get("id"), search.getProductId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, DaoUtils.toPaging(search));
    }
}
