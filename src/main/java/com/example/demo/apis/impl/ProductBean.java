package com.example.demo.apis.impl;

import com.example.demo.apis.Products;
import com.example.demo.apis.Security;
import com.example.demo.dao.OrganizationRepository;
import com.example.demo.dao.ProductRepository;
import com.example.demo.domain.LocalUser;
import com.example.demo.domain.Organization;
import com.example.demo.domain.Product;
import com.example.demo.dto.pagination.ProductSearch;
import com.example.demo.dto.product.ProductModifyDto;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.exceptions.LocalizedApplicationException;
import com.example.demo.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBean implements Products {

    private final ProductRepository productRepository;
    private final OrganizationRepository organizationRepository;
    private final Security security;

    @Autowired
    public ProductBean(ProductRepository productRepository, OrganizationRepository organizationRepository, Security security) {
        this.productRepository = productRepository;
        this.organizationRepository = organizationRepository;
        this.security = security;
    }

    @Override
    public void modify(ProductModifyDto modifyDto) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(modifyDto.getOrganizationId());
        if (optionalOrganization.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, modifyDto.getOrganizationId());
        }
        Organization organization = optionalOrganization.get();
        LocalUser currentUser = security.getCurrentUser();
        if (!organization.equals(currentUser.getOrganization())) {
            throw new LocalizedApplicationException(ErrorCode.VALUE_DID_NOT_MATCH, modifyDto.getOrganizationId());
        }

        Product product;
        if (ObjectUtils.isEmpty(modifyDto.getId())) {
            product = new Product();
        } else {
            product = get(modifyDto.getId());
        }
        product.setName(modifyDto.getName());
        product.setOrganization(optionalOrganization.get());
        productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, id));
    }

    @Override
    public Page<Product> search(ProductSearch search) {
        return productRepository.findAll((Specification<Product>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(search.getName())) {
                predicates.add(cb.like(root.get("name"), DaoUtils.toLikeCriteria(search.getName())));
            }

            if (!ObjectUtils.isEmpty(search.getOrganizationId())) {
                predicates.add(cb.equal(root.get("organization").get("id"), search.getOrganizationId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, DaoUtils.toPaging(search));
    }

    @Override
    public void delete(Long id) {
        Product product = get(id);
        productRepository.delete(product);
    }
}
