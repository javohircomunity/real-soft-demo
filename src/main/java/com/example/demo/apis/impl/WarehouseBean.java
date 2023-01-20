package com.example.demo.apis.impl;

import com.example.demo.apis.Warehouses;
import com.example.demo.dao.OrganizationRepository;
import com.example.demo.dao.WarehouseRepository;
import com.example.demo.domain.Organization;
import com.example.demo.domain.Warehouse;
import com.example.demo.dto.pagination.WarehouseSearch;
import com.example.demo.dto.warehouse.WarehouseModifyDto;
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
public class WarehouseBean implements Warehouses {

    private final WarehouseRepository warehouseRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public WarehouseBean(WarehouseRepository warehouseRepository, OrganizationRepository organizationRepository) {
        this.warehouseRepository = warehouseRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void modify(WarehouseModifyDto modifyDto) {
        Warehouse warehouse;
        if (ObjectUtils.isEmpty(modifyDto.getId())) {
            warehouse = new Warehouse();
        } else {
            warehouse = get(modifyDto.getId());
        }
        warehouse.setName(modifyDto.getName());

        Optional<Organization> optionalOrganization = organizationRepository.findById(modifyDto.getOrganizationId());
        if (optionalOrganization.isPresent()) {
            warehouse.setOrganization(optionalOrganization.get());
        } else {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, modifyDto.getOrganizationId());
        }
        warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse get(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, id));
    }

    @Override
    public Page<Warehouse> search(WarehouseSearch search) {
        return warehouseRepository.findAll((Specification<Warehouse>) (root, query, cb) -> {
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
        Warehouse warehouse = get(id);
        warehouseRepository.delete(warehouse);
    }
}
