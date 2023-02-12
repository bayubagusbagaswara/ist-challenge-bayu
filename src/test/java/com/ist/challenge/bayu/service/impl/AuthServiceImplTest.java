package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ConflictException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import com.ist.challenge.bayu.service.AuthService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(AuthServiceImplTest.class);

    @Autowired
    AuthService authService;

    @Test
    @Order(1)
    void registerSuccess() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("ronaldo");
        registerRequest.setPassword("ronaldo123");

        RegisterResponse register = authService.register(registerRequest);

        assertNotNull(register.getId());
    }

    @Test
    @Order(2)
    void registerFailedUsernameIsExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("ronaldo");
        registerRequest.setPassword("ronaldo12345");

        assertThrows(ConflictException.class, () -> {
            RegisterResponse register = authService.register(registerRequest);
        });
    }

    @Test
    @Order(3)
    void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ronaldo");
        loginRequest.setPassword("ronaldo123");

        LoginResponse loginResponse = authService.login(loginRequest);

        assertNotNull(loginResponse);
    }

    @Test
    @Order(4)
    void loginErrorUsernameIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("ronaldo123");

        assertThrows(BadRequestException.class, () -> {
            LoginResponse login = authService.login(loginRequest);
        });
    }

    @Test
    @Order(5)
    void loginErrorPasswordIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ronaldo");
        loginRequest.setPassword("");

        assertThrows(BadRequestException.class, () -> {
            LoginResponse loginResponse = authService.login(loginRequest);
        });
    }

    @Test
    @Order(6)
    void loginErrorPasswordNotMatching() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ronaldo");
        loginRequest.setPassword("ronaldo1234");

        assertThrows(UnauthorizedException.class, () -> {
            LoginResponse loginResponse = authService.login(loginRequest);
        });
    }

}