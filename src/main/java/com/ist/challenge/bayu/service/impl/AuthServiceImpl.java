package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ResourceNotFoundException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import com.ist.challenge.bayu.model.User;
import com.ist.challenge.bayu.repository.UserRepository;
import com.ist.challenge.bayu.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());

        userRepository.save(user);
        return RegisterResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public MessageResponse login(LoginRequest loginRequest) {
        checkUsernameOrPasswordIsEmpty(loginRequest);

        checkUsernameAndPasswordIsMatching(loginRequest);

        return new MessageResponse(Boolean.TRUE, "Login successful");
    }

    // hanya memanggil UserService tanpa langsung UserRepository

    public void checkUsernameOrPasswordIsEmpty(LoginRequest loginRequest) {
        if (loginRequest.getUsername().equalsIgnoreCase("")
                || loginRequest.getUsername().isEmpty()) {
            throw new BadRequestException(new MessageResponse(Boolean.FALSE, "Username is empty"));
        }

        if (loginRequest.getPassword().equalsIgnoreCase("")
                || loginRequest.getPassword().isEmpty()) {
            throw new BadRequestException(new MessageResponse(Boolean.FALSE, "Password is empty"));
        }
    }

    // check username and password is matching
    public void checkUsernameAndPasswordIsMatching(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(new MessageResponse(Boolean.FALSE, "User not found with username : " + loginRequest.getUsername())));

        if (!user.getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
            throw new UnauthorizedException(new MessageResponse(Boolean.FALSE, "Password does not match"));
        }
    }
}
