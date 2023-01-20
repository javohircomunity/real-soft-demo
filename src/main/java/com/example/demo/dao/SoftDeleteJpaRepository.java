package com.example.demo.dao;

import com.example.demo.domain.AbstractEntity;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.exceptions.LocalizedApplicationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface SoftDeleteJpaRepository<T extends AbstractEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Override
    @Transactional
    @Modifying
    default void deleteById(Long id) {
        findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND)).setDeleted(true);
    }

    @Override
    @Transactional
    default void delete(T entity) {
        entity.setDeleted(true);
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Transactional
    @Modifying
    default void softDeleteAll() {
        this.deleteAll(findAll());
    }

}
