package com.example.demo.dao;

import com.example.demo.domain.LocalUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeleteJpaRepository<LocalUser> {

    Optional<LocalUser> findByUsername(String username);

    Optional<LocalUser> findByUsernameAndIdNot(String username, Long id);
}
