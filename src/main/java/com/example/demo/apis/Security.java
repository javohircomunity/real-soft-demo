package com.example.demo.apis;

import com.example.demo.domain.LocalUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface Security extends UserDetailsService {

    LocalUser getCurrentUser();

}
