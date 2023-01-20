package com.example.demo.apis.impl;

import com.example.demo.apis.Organizations;
import com.example.demo.dao.OrganizationRepository;
import com.example.demo.domain.Organization;
import com.example.demo.dto.organization.OrganizationModifyDto;
import com.example.demo.dto.pagination.OrganizationSearch;
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

@Service
public class OrganizationBean implements Organizations {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationBean(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void modify(OrganizationModifyDto modifyDto) {
        if (ObjectUtils.isEmpty(modifyDto.getName())) {
            throw new LocalizedApplicationException(ErrorCode.REQUIRED_FIELD, "name");
        }
        Organization organization;
        if (ObjectUtils.isEmpty(modifyDto.getId())) {
            organization = new Organization();
        } else {
            organization = get(modifyDto.getId());
        }
        organization.setName(modifyDto.getName());
        organizationRepository.save(organization);
    }

    @Override
    public Organization get(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, id));
    }

    @Override
    public Page<Organization> search(OrganizationSearch search) {
        return organizationRepository.findAll((Specification<Organization>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(search.getName())) {
                predicates.add(cb.like(root.get("name"), DaoUtils.toLikeCriteria(search.getName())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, DaoUtils.toPaging(search));
    }

    @Override
    public void delete(Long id) {
        Organization organization = get(id);
        organizationRepository.delete(organization);
    }
}
