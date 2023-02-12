package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.service.AuthService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    private final static Logger log = LoggerFactory.getLogger(AuthControllerTest.class);

    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;

    RegisterRequest registerRequest;
    RegisterResponse registerResponse;

    LoginRequest loginRequest;
    LoginResponse loginResponse;

    @Test
    @Order(1)
    void register() {
        registerRequest = new RegisterRequest("bayu", "bayu123");
        registerResponse = new RegisterResponse(1L, "bayu", "bayu123");

        when(authService.register(registerRequest))
                .thenReturn(registerResponse);

        ResponseEntity<MessageResponse> apiResponse = authController.register(registerRequest);

        assertEquals(HttpStatus.CREATED, apiResponse.getStatusCode());

        log.info("Code: {}", Objects.requireNonNull(apiResponse.getBody()).getCode());
        log.info("Success: {}", apiResponse.getBody().getSuccess());
        log.info("Message: {}", apiResponse.getBody().getMessage());
    }

    @Test
    @Order(2)
    void login() {
        loginRequest = new LoginRequest("bayu", "bayu123");
        loginResponse = new LoginResponse(1L, "bayu", "bayu123");

        when(authService.login(loginRequest))
                .thenReturn(loginResponse);

        ResponseEntity<MessageResponse> apiResponse = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());

        log.info("Code: {}", Objects.requireNonNull(apiResponse.getBody()).getCode());
        log.info("Success: {}", apiResponse.getBody().getSuccess());
        log.info("Message: {}", apiResponse.getBody().getMessage());
    }

}