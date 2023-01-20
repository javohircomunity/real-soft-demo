package com.example.demo.apis.impl;

import com.example.demo.apis.Security;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.LocalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.demo.exceptions.ErrorCode.USERNAME_NOT_FOUND;

@Component
public class SecurityBean implements Security {

    private static LocalUser currentUser;
    private final UserRepository userRepository;

    public SecurityBean(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LocalUser getCurrentUser() {
        if (currentUser != null) {
            return currentUser;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("Should be authenticated!");
        }
        return loadUserByUsername(authentication.getName());
    }

    @Override
    public LocalUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> userByLogin = getUserByLogin(username);
        if (userByLogin.isPresent()) {
            LocalUser localUser = userByLogin.get();
            currentUser = localUser;
            return localUser;
        } else {
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND.name());
        }
    }

    private Optional<LocalUser> getUserByLogin(String username) {
        return userRepository.findByUsername(username);
    }
}
