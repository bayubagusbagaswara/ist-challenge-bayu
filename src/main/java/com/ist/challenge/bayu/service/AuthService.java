package com.ist.challenge.bayu.service;

import com.ist.challenge.bayu.dto.RegisterRequest;
import com.ist.challenge.bayu.dto.RegisterResponse;

public interface AuthService {

    // register
    RegisterResponse register(RegisterRequest registerRequest);

    // login
}
