package com.ist.challenge.bayu.service;

import com.ist.challenge.bayu.dto.CreateUserRequest;
import com.ist.challenge.bayu.dto.CreateUserResponse;
import com.ist.challenge.bayu.dto.UserResponse;

import java.util.List;

public interface UserService {

    // create new user
    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    // get user by id
    UserResponse getUserById(Long id);

    // get all user
    List<UserResponse> getAllUsers();

    // get all user by pagination and sorting

    // update user

    // delete user
}
