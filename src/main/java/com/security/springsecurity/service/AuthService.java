package com.security.springsecurity.service;

import com.security.springsecurity.dto.AuthenticationRequest;
import com.security.springsecurity.dto.AuthenticationResponse;
import com.security.springsecurity.dto.RegisterRequest;
import com.security.springsecurity.entity.User;
import com.security.springsecurity.enums.Role;
import com.security.springsecurity.mapper.AuthenticationResponseMapper;
import com.security.springsecurity.mapper.RoleMapper;
import com.security.springsecurity.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationResponse register(@Valid RegisterRequest registerRequest) {
        Set<Role> roles = RoleMapper.getRole(registerRequest.getRoles());
        User user = userService.registerUser(registerRequest.getEmail(), registerRequest.getPassword(), roles, registerRequest.getUsername());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        AuthenticationResponse authenticationResponse = AuthenticationResponseMapper.getAuthResponse(user, token);
        return authenticationResponse;
    }

    public AuthenticationResponse login(@Valid AuthenticationRequest authenticationRequest) {
        User user = userService.findUserByEmail(authenticationRequest.getEmail());
        String token = jwtUtil.generateToken(authenticationRequest.getEmail(), user.getRole());

        AuthenticationResponse authenticationResponse = AuthenticationResponseMapper.getAuthResponse(user, token);
        return authenticationResponse;
    }
}
