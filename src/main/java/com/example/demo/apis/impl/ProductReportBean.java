package com.example.demo.apis.impl;

import com.example.demo.apis.ProductReports;
import com.example.demo.dao.ProductItemRepository;
import com.example.demo.dao.ProductReportRepository;
import com.example.demo.dao.ProductRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.LocalUser;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductItem;
import com.example.demo.domain.ProductReport;
import com.example.demo.dto.pagination.ProductReportSearch;
import com.example.demo.dto.pagination.ProductSearch;
import com.example.demo.dto.productReport.ProductReportInOutDto;
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
public class ProductReportBean implements ProductReports {

    private final ProductReportRepository productReportRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductReportBean(ProductReportRepository productReportRepository, ProductItemRepository productItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.productReportRepository = productReportRepository;
        this.productItemRepository = productItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void productIn(ProductReportInOutDto inOutDto) {
        checkValidation(inOutDto);
        Optional<Product> optionalProduct = productRepository.findById(inOutDto.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, inOutDto.getProductId());
        }

        Optional<LocalUser> optionalLocalUser = userRepository.findById(inOutDto.getUserId());
        if (optionalLocalUser.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, inOutDto.getProductId());
        }
        LocalUser localUser = optionalLocalUser.get();
        ProductReport productReport = new ProductReport(inOutDto.getCount(), inOutDto.getActionType(), optionalProduct.get(), localUser, localUser.getOrganization());

        productReportRepository.save(productReport);

        Optional<ProductItem> optionalProductItem = productItemRepository.findByProduct(productReport.getProduct());
        ProductItem productItem;
        if (optionalProductItem.isEmpty()) {
            productItem = new ProductItem();
            productItem.setCount(productReport.getCount());
            productItem.setProduct(productReport.getProduct());
            productItem.setOrganization(productReport.getOrganization());
        } else {
            productItem = optionalProductItem.get();
            productItem.setCount(productItem.getCount() + productReport.getCount());
        }
        productItemRepository.save(productItem);
    }

    @Override
    public void productOut(ProductReportInOutDto inOutDto) {
        checkValidation(inOutDto);
        Optional<Product> optionalProduct = productRepository.findById(inOutDto.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, inOutDto.getProductId());
        }

        Optional<LocalUser> optionalLocalUser = userRepository.findById(inOutDto.getUserId());
        if (optionalLocalUser.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, inOutDto.getProductId());
        }

        Optional<ProductItem> optionalProductItem = productItemRepository.findByProduct(optionalProduct.get());
        if (optionalProductItem.isEmpty()) {
            throw new LocalizedApplicationException(ErrorCode.HAS_NOT_PRODUCT_IN_WAREHOUSE, inOutDto.getProductId());
        }

        ProductItem productItem = optionalProductItem.get();
        if (productItem.getCount() < inOutDto.getCount()) {
            throw new LocalizedApplicationException(ErrorCode.HAS_NOT_ENOUGH_PRODUCT_IN_WAREHOUSE);
        }

        productItem.setCount(productItem.getCount() - inOutDto.getCount());
        productItemRepository.save(productItem);

        LocalUser localUser = optionalLocalUser.get();
        ProductReport productReport = new ProductReport(inOutDto.getCount(), inOutDto.getActionType(), optionalProduct.get(), localUser, localUser.getOrganization());

        productReportRepository.save(productReport);
    }

    @Override
    public ProductReport get(Long id) {
        return productReportRepository.findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, id));
    }

    @Override
    public Page<ProductReport> search(ProductReportSearch search) {
        return productReportRepository.findAll((Specification<ProductReport>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(search.getActionType())) {
                predicates.add(cb.equal(root.get("actionType"), search.getActionType().ordinal()));
            }

            if (!ObjectUtils.isEmpty(search.getOrganizationId())) {
                predicates.add(cb.equal(root.get("organization").get("id"), search.getOrganizationId()));
            }

            if (!ObjectUtils.isEmpty(search.getOrganizationId())) {
                predicates.add(cb.equal(root.get("product").get("id"), search.getOrganizationId()));
            }

            if (!ObjectUtils.isEmpty(search.getOrganizationId())) {
                predicates.add(cb.equal(root.get("localUser").get("id"), search.getOrganizationId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, DaoUtils.toPaging(search));
    }

    private <T extends ProductReportInOutDto> void checkValidation(T dto) {
        if (ObjectUtils.isEmpty(dto.getProductId())) {
            throw new LocalizedApplicationException(ErrorCode.REQUIRED_FIELD, "product");
        }
        if (ObjectUtils.isEmpty(dto.getUserId())) {
            throw new LocalizedApplicationException(ErrorCode.REQUIRED_FIELD, "user");
        }
        if (ObjectUtils.isEmpty(dto.getActionType())) {
            throw new LocalizedApplicationException(ErrorCode.REQUIRED_FIELD, "actionType");
        }
        if (ObjectUtils.isEmpty(dto.getCount())) {
            throw new LocalizedApplicationException(ErrorCode.REQUIRED_FIELD, "count");
        }
    }
}
