package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import com.ist.challenge.bayu.service.AuthService;
import com.ist.challenge.bayu.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build();

        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);

        return RegisterResponse.builder()
                .id(createUserResponse.getId())
                .username(createUserRequest.getUsername())
                .password(createUserResponse.getPassword())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        checkUsernameOrPasswordIsEmpty(loginRequest);

        checkUsernameAndPasswordIsMatching(loginRequest);

        return LoginResponse.builder()
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .build();
    }

    public void checkUsernameOrPasswordIsEmpty(LoginRequest loginRequest) {
        if (loginRequest.getUsername().equalsIgnoreCase("")
                || loginRequest.getUsername().isEmpty()
                || loginRequest.getPassword().equalsIgnoreCase("")
                || loginRequest.getPassword().isEmpty()) {
            throw new BadRequestException("Username atau Password tidak boleh kosong");
        }

    }

    public void checkUsernameAndPasswordIsMatching(LoginRequest loginRequest) {
        UserResponse user = userService.getUserByUsername(loginRequest.getUsername());

        if (!user.getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
            throw new UnauthorizedException("Password tidak cocok dengan username : " + loginRequest.getUsername());
        }
    }

}
