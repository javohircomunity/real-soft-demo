package com.example.demo.web;

import com.example.demo.apis.Organizations;
import com.example.demo.domain.Organization;
import com.example.demo.dto.organization.OrganizationDto;
import com.example.demo.dto.organization.OrganizationModifyDto;
import com.example.demo.dto.pagination.OrganizationSearch;
import com.example.demo.mapping.OrganizationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/organizations")
public class OrganizationController {

    private final Organizations organizations;
    private final OrganizationMapper organizationMapper;

    @Autowired
    public OrganizationController(Organizations organizations, OrganizationMapper organizationMapper) {
        this.organizations = organizations;
        this.organizationMapper = organizationMapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PostMapping(value = "search", produces = "application/json")
    public Page<OrganizationDto> search(@RequestBody OrganizationSearch search) {
        log.trace("Accessing POST api/organizations/search/{}", search);

        Page<Organization> foundOrganization = organizations.search(search);

        log.trace("{} organizations found and {} returned to front-end", foundOrganization.getTotalElements(), foundOrganization.getSize());

        return foundOrganization.map(organizationMapper::toDto);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @GetMapping(value = "get/{organizationId}", produces = "application/json")
    public OrganizationDto get(@PathVariable Long organizationId) {
        log.trace("Accessing GET api/organizations/get/{}", organizationId);

        Organization organization = organizations.get(organizationId);

        log.trace("{} get organization and returned to front-end", organization);

        return organizationMapper.toDto(organization);
    }

    @Transactional
    @PostMapping(value = "modify", produces = "application/json")
    public void modify(@RequestBody OrganizationModifyDto dto) {
        log.trace("Accessing POST api/organizations/modify/{} ", dto.toJsonString());

        organizations.modify(dto);

        log.trace("Organization modified");
    }

    @DeleteMapping(value = "delete/{organizationId}", produces = "application/json")
    public void delete(@PathVariable Long organizationId) {

        log.trace("Accessing DELETE api/organizations/delete/{}", organizationId);

        organizations.delete(organizationId);

        log.trace("{} deleted organization and returned void to front-end", organizationId);
    }
}
