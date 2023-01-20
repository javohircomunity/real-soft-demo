package com.example.demo.apis.impl;

import com.example.demo.apis.Users;
import com.example.demo.dao.OrganizationRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.LocalUser;
import com.example.demo.domain.Organization;
import com.example.demo.dto.pagination.UserSearch;
import com.example.demo.dto.user.UserModifyDto;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.exceptions.LocalizedApplicationException;
import com.example.demo.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserBean implements Users {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserBean(UserRepository userRepository, OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void modify(UserModifyDto modifyDto) {
        LocalUser localUser;
        if (ObjectUtils.isEmpty(modifyDto.getId())) {
            Optional<LocalUser> byUsername = userRepository.findByUsername(modifyDto.getUsername());
            if (byUsername.isPresent()) {
                throw new LocalizedApplicationException(ErrorCode.FIELD_VALUE_UNIQUE, "username");
            }
            localUser = new LocalUser();
            localUser.setUsername(modifyDto.getUsername());
            localUser.setPassword(passwordEncoder.encode(modifyDto.getPassword()));
        } else {
            localUser = get(modifyDto.getId());
            if (!ObjectUtils.isEmpty(modifyDto.getUsername())) {
                Optional<LocalUser> optionalLocalUser = userRepository.findByUsernameAndIdNot(modifyDto.getUsername(), modifyDto.getId());
                if (optionalLocalUser.isPresent()) {
                    throw new LocalizedApplicationException(ErrorCode.FIELD_VALUE_UNIQUE, "username");
                } else {
                    localUser.setUsername(modifyDto.getUsername());
                }
            }
            if (!ObjectUtils.isEmpty(modifyDto.getPassword())) {
                localUser.setPassword(passwordEncoder.encode(modifyDto.getPassword()));
            }
        }

        localUser.setFirstName(modifyDto.getFirstName());
        localUser.setLastName(modifyDto.getFirstName());

        Optional<Organization> optionalOrganization = organizationRepository.findById(modifyDto.getOrganizationId());
        if (optionalOrganization.isPresent()) {
            localUser.setOrganization(optionalOrganization.get());
        } else {
            throw new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, modifyDto.getOrganizationId());
        }
        userRepository.save(localUser);
    }

    @Override
    public LocalUser get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, id));
    }

    @Override
    public Page<LocalUser> search(UserSearch search) {
        return userRepository.findAll((Specification<LocalUser>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(search.getUsername())) {
                predicates.add(cb.like(root.get("username"), DaoUtils.toLikeCriteria(search.getUsername())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, DaoUtils.toPaging(search));
    }

    @Override
    public void delete(Long id) {
        LocalUser localUser = get(id);
        userRepository.delete(localUser);
    }
}
