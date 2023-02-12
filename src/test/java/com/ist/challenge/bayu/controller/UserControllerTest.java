package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    private final static Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    CreateUserRequest createUserRequest;
    CreateUserResponse createUserResponse;

    UpdateUserRequest updateUserRequest;
    UpdateUserResponse updateUserResponse;

    UserResponse userResponse;
    List<UserResponse> userResponseList;

    ListUserRequest listUserRequest;
    ListUserResponse listUserResponse;

    @Test
    @Order(1)
    void createUser() {
        createUserRequest = new CreateUserRequest("bayu", "bayu123");
        createUserResponse = new CreateUserResponse(1L, "bayu", "bayu123");

        when(userService.createUser(createUserRequest))
                .thenReturn(createUserResponse);

        ResponseEntity<MessageResponse> apiResponse = userController.createUser(createUserRequest);

        assertEquals(HttpStatus.CREATED, apiResponse.getStatusCode());
        log.info("Code: {}", Objects.requireNonNull(apiResponse.getBody()).getCode());
        log.info("Success: {}", apiResponse.getBody().getSuccess());
        log.info("Message: {}", apiResponse.getBody().getMessage());
    }

    @Test
    @Order(2)
    void getUserById() {

        userResponse = new UserResponse(1L, "bayu", "bayu123");
        Long userId = 1L;

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        ResponseEntity<ApiResponse<UserResponse>> apiResponse = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
        assertEquals(userId, Objects.requireNonNull(apiResponse.getBody()).getData().getId());
    }

    @Test
    @Order(3)
    void getUserByUsername() {
        userResponse = new UserResponse(1L, "bayu", "bayu123");
        String username = "bayu";

        when(userService.getUserByUsername(username))
                .thenReturn(userResponse);

        ResponseEntity<ApiResponse<UserResponse>> apiResponse = userController.getUserByUsername(username);

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
        assertEquals(username, Objects.requireNonNull(apiResponse.getBody()).getData().getUsername());
    }

    @Test
    @Order(4)
    void getUsersUsernameLike() {
        String username = "ba";
        userResponseList = new ArrayList<>();
        userResponseList.add(new UserResponse(1L, "bayu", "bayu123"));
        userResponseList.add(new UserResponse(2L, "bagus", "bagus123"));

        when(userService.getUsersByUsernameContains(username))
                .thenReturn(userResponseList);

        ResponseEntity<ApiResponse<List<UserResponse>>> apiResponse = userController.getUsersUsernameLike(username);

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
        assertEquals(2, Objects.requireNonNull(apiResponse.getBody()).getData().size());

        for (UserResponse user : apiResponse.getBody().getData()) {
            log.info("Username: {}", user.getUsername());
        }
    }

    @Test
    @Order(5)
    void getAllUsers() {
        userResponseList = new ArrayList<>();
        userResponseList.add(new UserResponse(1L, "bayu", "bayu123"));
        userResponseList.add(new UserResponse(2L, "bagus", "bagus123"));
        userResponseList.add(new UserResponse(3L, "albert", "albert123"));

        when(userService.getAllUsers())
                .thenReturn(userResponseList); // Mocking

        ResponseEntity<ApiResponse<List<UserResponse>>> apiResponse = userController.getAllUsers();

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
        assertEquals(3, Objects.requireNonNull(apiResponse.getBody()).getData().size());

        log.info("Code: {}", apiResponse.getBody().getCode());
        log.info("Success: {}", apiResponse.getBody().getSuccess());
        log.info("Message: {}", apiResponse.getBody().getMessage());
        log.info("Data: {}", apiResponse.getBody().getData());
    }

    @Test
    @Order(6)
    void getAllUsersPage() {
        userResponseList = new ArrayList<>();
        userResponseList.add(new UserResponse(1L, "bayu", "bayu123"));
        userResponseList.add(new UserResponse(2L, "bagus", "bagus123"));
        userResponseList.add(new UserResponse(3L, "albert", "albert123"));

        int pageNo = 0;
        int pageSize = 3;
        String sortBy = "id";
        String sortDir = "asc";

        listUserRequest = new ListUserRequest(pageNo, pageSize, sortBy, sortDir);

        listUserResponse = ListUserResponse.builder()
                .users(userResponseList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(2)
                .totalElements(3L)
                .last(true)
                .build();

        when(userService.listAllUsers(listUserRequest))
                .thenReturn(listUserResponse);

        ResponseEntity<ApiResponse<ListUserResponse>> apiResponse = userController.getAllUsersPage(pageNo, pageSize, sortBy, sortDir);

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
        assertEquals(3, apiResponse.getBody().getData().getTotalElements());

        for (UserResponse user : apiResponse.getBody().getData().getUsers()) {
            log.info("Username : {}", user.getUsername());
        }
    }

    @Test
    @Order(7)
    void updateUser() {

        userResponse = new UserResponse(1L, "bayu", "bayu123");

        updateUserRequest = new UpdateUserRequest("bagus", "bagus123");
        Long userId = 1L;

        updateUserResponse = new UpdateUserResponse(1L, "bagus", "bagus123");

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        when(userService.updateUser(userId, updateUserRequest))
                .thenReturn(updateUserResponse);

        ResponseEntity<ApiResponse<UpdateUserResponse>> apiResponse = userController.updateUser(userId, updateUserRequest);

        assertEquals(HttpStatus.CREATED, apiResponse.getStatusCode());

        log.info("Code: {}", apiResponse.getBody().getCode());
        log.info("Success: {}", apiResponse.getBody().getSuccess());
        log.info("Message: {}", apiResponse.getBody().getMessage());
        log.info("Data: {}", apiResponse.getBody().getData());
    }

    @Test
    @Order(8)
    void deleteUser() {
        userResponse = new UserResponse(1L, "bayu", "bayu123");
        Long userId = 1L;

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        ResponseEntity<MessageResponse> apiResponse = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
        log.info("Code: {}", apiResponse.getBody().getCode());
        log.info("Success: {}", apiResponse.getBody().getSuccess());
        log.info("Message: {}", apiResponse.getBody().getMessage());
    }

}