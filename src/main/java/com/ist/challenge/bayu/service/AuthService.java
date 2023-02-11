package com.ist.challenge.bayu.service;

import com.ist.challenge.bayu.dto.*;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
