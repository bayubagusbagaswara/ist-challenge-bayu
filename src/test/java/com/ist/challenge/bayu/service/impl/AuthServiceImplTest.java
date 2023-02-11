package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.LoginRequest;
import com.ist.challenge.bayu.dto.MessageResponse;
import com.ist.challenge.bayu.dto.RegisterRequest;
import com.ist.challenge.bayu.dto.RegisterResponse;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import com.ist.challenge.bayu.service.AuthService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(AuthServiceImplTest.class);

    @Autowired
    AuthService authService;

    @Test
    void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("albert");
        registerRequest.setPassword("albert123");

        RegisterResponse register = authService.register(registerRequest);

        assertNotNull(register.getId());
    }

    @Test
    void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("albert");
        loginRequest.setPassword("albert123");

        MessageResponse messageResponse = authService.login(loginRequest);

        assertTrue(messageResponse.getSuccess());
        log.info("Message: {}", messageResponse.getMessage());
    }

    // login error Username is Empty
    @Test
    void loginErrorUsernameIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("bayu123");

        assertThrows(BadRequestException.class, () -> {
            MessageResponse login = authService.login(loginRequest);
        });
    }

    @Test
    void loginErrorPasswordIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bayu");
        loginRequest.setPassword("");

        assertThrows(BadRequestException.class, () -> {
            MessageResponse login = authService.login(loginRequest);
        });
    }

    @Test
    void loginErrorPasswordNotMatching() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("albert");
        loginRequest.setPassword("albert1234");

        assertThrows(UnauthorizedException.class, () -> {
            MessageResponse login = authService.login(loginRequest);
        });
    }

}