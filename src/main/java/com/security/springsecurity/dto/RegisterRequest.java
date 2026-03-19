package com.security.springsecurity.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {


    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 to 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Size(min = 3, max = 50, message = "Email must be between 3 to 20 characters")
    private String email;

    private String password;

    private Set<String> roles;

}
