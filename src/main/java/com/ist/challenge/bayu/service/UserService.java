package com.ist.challenge.bayu.service;

import com.ist.challenge.bayu.dto.*;

import java.util.List;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse getUserByUsername(String username);

    List<UserResponse> getUsersByUsernameContains(String username);

    ListUserResponse listAllUsers(ListUserRequest listUserRequest);

    UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

    void deleteUserById(Long id);

}
