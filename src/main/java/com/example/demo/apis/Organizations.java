package com.example.demo.apis;

import com.example.demo.domain.Organization;
import com.example.demo.dto.organization.OrganizationModifyDto;
import com.example.demo.dto.pagination.OrganizationSearch;
import org.springframework.data.domain.Page;

public interface Organizations {

    void modify(OrganizationModifyDto modifyDto);

    Organization get(Long id);

    Page<Organization> search(OrganizationSearch search);

    void delete(Long id);
}
