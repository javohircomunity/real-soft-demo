package com.example.demo.dao;

import com.example.demo.domain.Organization;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends SoftDeleteJpaRepository<Organization> {
}
