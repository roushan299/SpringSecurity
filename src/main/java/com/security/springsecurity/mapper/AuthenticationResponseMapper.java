package com.security.springsecurity.mapper;

import com.security.springsecurity.dto.AuthenticationResponse;
import com.security.springsecurity.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationResponseMapper {
    public static AuthenticationResponse getAuthResponse(User user, String token) {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(new ArrayList<>(user.getRole()))
                .id(user.getId())
                .build();
        return authenticationResponse;
    }






}
