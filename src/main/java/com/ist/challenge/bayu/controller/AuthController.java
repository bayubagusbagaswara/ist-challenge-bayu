package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse register = authService.register(registerRequest);
        MessageResponse messageResponse = MessageResponse.builder()
                .code(201)
                .success(Boolean.TRUE)
                .message("Register berhasil dengan username : " + register.getUsername())
                .build();

        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse login = authService.login(loginRequest);
        MessageResponse messageResponse = MessageResponse.builder()
                .code(200)
                .success(Boolean.TRUE)
                .message("Login berhasil dengan username : " + login.getUsername())
                .build();

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

}
