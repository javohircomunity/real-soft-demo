package com.example.demo.web;

import com.example.demo.apis.Security;
import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.exceptions.UnAuthorizedException;
import com.example.demo.filter.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final Security security;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, Security security) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.security = security;
    }

    @PostMapping("/api/auth")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new UnAuthorizedException(ErrorCode.UN_AUTHORIZED, "Can not authenticate");
        }

        UserDetails userDetails = security.loadUserByUsername(authenticationRequest.getUserName());

        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
