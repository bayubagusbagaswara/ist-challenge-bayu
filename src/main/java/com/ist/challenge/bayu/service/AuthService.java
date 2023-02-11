package com.ist.challenge.bayu.service;

import com.ist.challenge.bayu.dto.*;

public interface AuthService {

    // register
    RegisterResponse register(RegisterRequest registerRequest);

    // login
    MessageResponse login(LoginRequest loginRequest);
}
