package com.ist.challenge.bayu.service;

import com.ist.challenge.bayu.dto.*;

import java.util.List;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    ListUserResponse listAllUsers(ListUserRequest listUserRequest);

    UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

    void deleteUserById(Long id);

}
