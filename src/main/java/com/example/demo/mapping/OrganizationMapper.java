package com.example.demo.mapping;

import com.example.demo.domain.Organization;
import com.example.demo.dto.organization.OrganizationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationDto toDto(Organization organization);
}
