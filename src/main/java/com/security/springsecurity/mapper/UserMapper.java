package com.security.springsecurity.mapper;

import com.security.springsecurity.dto.UserResponse;
import com.security.springsecurity.entity.User;

public class UserMapper {

    public static UserResponse getUserResponse(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRole())
                .build();
        return userResponse;
    }

}
