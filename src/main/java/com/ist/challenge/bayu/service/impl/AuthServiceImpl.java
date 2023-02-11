package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.RegisterRequest;
import com.ist.challenge.bayu.dto.RegisterResponse;
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
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);
        return RegisterResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    // hanya memanggil UserService tanpa langsung UserRepository
}
