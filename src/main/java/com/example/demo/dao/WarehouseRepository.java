package com.example.demo.dao;

import com.example.demo.domain.Warehouse;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends SoftDeleteJpaRepository<Warehouse> {
}
