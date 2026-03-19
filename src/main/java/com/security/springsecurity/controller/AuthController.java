package com.security.springsecurity.controller;

import com.security.springsecurity.dto.AuthenticationRequest;
import com.security.springsecurity.dto.AuthenticationResponse;
import com.security.springsecurity.dto.RegisterRequest;
import com.security.springsecurity.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try{
            AuthenticationResponse response = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    private ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try{
            AuthenticationResponse response = authService.login(authenticationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
