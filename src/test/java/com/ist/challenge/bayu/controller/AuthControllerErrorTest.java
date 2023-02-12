package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ConflictException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerErrorTest {

    private final static Logger log = LoggerFactory.getLogger(AuthControllerErrorTest.class);

    @Autowired
    AuthController authController;

    @Test
    void registerFailedUsernameIsExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("bagus");
        registerRequest.setPassword("bagus1234567");

        assertThrows(ConflictException.class, () -> {
            ResponseEntity<MessageResponse> register = authController.register(registerRequest);
        });
    }

    @Test
    void loginFailedUsernameOrPasswordIsBlank() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("");

        assertThrows(BadRequestException.class, () -> {
            ResponseEntity<MessageResponse> login = authController.login(loginRequest);
        });
    }

    @Test
    void loginFailedPasswordDoesNotMatchWithUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bagus");
        loginRequest.setPassword("bagus1234");

        assertThrows(UnauthorizedException.class, () -> {
            ResponseEntity<MessageResponse> login = authController.login(loginRequest);
        });
    }

}