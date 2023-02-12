package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.UsernameAlreadyExistsException;
import com.ist.challenge.bayu.exception.ResourceNotFoundException;
import com.ist.challenge.bayu.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    UserService userService;

    @Test
    @Order(1)
    void createUserSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("bayu");
        createUserRequest.setPassword("bayu123");

        CreateUserRequest createUserRequest1 = new CreateUserRequest();
        createUserRequest1.setUsername("bagus");
        createUserRequest1.setPassword("bagus123");

        CreateUserRequest createUserRequest2 = new CreateUserRequest();
        createUserRequest2.setUsername("albert");
        createUserRequest2.setPassword("albert123");

        CreateUserRequest createUserRequest3 = new CreateUserRequest();
        createUserRequest3.setUsername("john");
        createUserRequest3.setPassword("john123");

        CreateUserResponse user = userService.createUser(createUserRequest);
        CreateUserResponse user1 = userService.createUser(createUserRequest1);
        CreateUserResponse user2 = userService.createUser(createUserRequest2);
        CreateUserResponse user3 = userService.createUser(createUserRequest3);

        assertNotNull(user.getId());
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
        assertNotNull(user3.getId());

        assertEquals(createUserRequest.getUsername(), user.getUsername());
        assertEquals(createUserRequest1.getUsername(), user1.getUsername());
        assertEquals(createUserRequest2.getUsername(), user2.getUsername());
        assertEquals(createUserRequest3.getUsername(), user3.getUsername());

        log.info("User 1: {}", user.getUsername());
        log.info("User 2: {}", user1.getUsername());
        log.info("User 3: {}", user2.getUsername());
        log.info("User 4: {}", user3.getUsername());
    }

    @Test
    @Order(2)
    void createUserFailedUsernameIsExists() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("bayu");
        createUserRequest.setPassword("bbb123");

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            CreateUserResponse user = userService.createUser(createUserRequest);
        });
    }

    @Test
    @Order(3)
    void getUserById() {
        Long id = 1L;

        UserResponse user = userService.getUserById(id);

        assertNotNull(user);
        assertEquals(id, user.getId());

        log.info("Username: {}", user.getUsername());
    }

    @Test
    @Order(4)
    void getUserByUsername() {
        String username = "bayu"; // tidak ignore case

        UserResponse user = userService.getUserByUsername(username);

        assertNotNull(user);
        assertEquals(username, user.getUsername());

        log.info("Username: {}", user.getUsername());
    }

    @Test
    @Order(5)
    void getUsersByUsernameContains() {
        String username = "ba";

        List<UserResponse> users = userService.getUsersByUsernameContains(username);

        assertEquals(2, users.size());

        for (UserResponse user : users) {
            log.info("Username: {}", user.getUsername() );
        }
    }

    @Test
    @Order(6)
    void getAllUsers() {
        int totalSampleData = 4;

        List<UserResponse> allUsers = userService.getAllUsers();

        assertEquals(totalSampleData, allUsers.size());

        for (UserResponse user : allUsers) {
            log.info("Username: {}", user.getUsername());
        }
    }

    @Test
    @Order(7)
    void listAllUsers() {
        int totalSample = 3;

        ListUserRequest listUserRequest = new ListUserRequest();
        listUserRequest.setPageNo(0);
        listUserRequest.setPageSize(2);
        listUserRequest.setSortBy("username");
        listUserRequest.setSortDir("asc");

        ListUserResponse listAllUsers = userService.listAllUsers(listUserRequest);

        assertEquals(2, listAllUsers.getUsers().size()); // yang ditampilkan hanya 2 data

        for (UserResponse user : listAllUsers.getUsers()) {
            log.info("Username: {}", user.getUsername());
        }
    }

    @Test
    @Order(8)
    void updateUserSuccess() {
        Long id = 3L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("alberto");
        updateUserRequest.setPassword("alberto123");

        UpdateUserResponse userResponse = userService.updateUser(id, updateUserRequest);

        assertEquals(id, userResponse.getId());
        assertEquals(updateUserRequest.getUsername(), userResponse.getUsername());
        assertEquals(updateUserRequest.getPassword(), userResponse.getPassword());

        log.info("Username: {}", userResponse.getUsername());
    }

    @Test
    @Order(9)
    void updateUserErrorUsernameIsExists() {
        Long id = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("bagus"); // username lama bayu
        updateUserRequest.setPassword("viento12");

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UpdateUserResponse user = userService.updateUser(id, updateUserRequest);
        });
    }

    // update error password baru tidak boleh dengan password lama
    @Test
    @Order(10)
    void updateUserErrorNewPasswordIsTheSameAsTheOldOne() {
        Long id = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("bayubagaswara"); // username lama bayu
        updateUserRequest.setPassword("bayu123");

        assertThrows(BadRequestException.class, () -> {
            UpdateUserResponse user = userService.updateUser(id, updateUserRequest);
        });
    }

    @Test
    @Order(11)
    void deleteUserById() {
        Long id = 1L;

        userService.deleteUserById(id);

        assertThrows(ResourceNotFoundException.class, () -> {
            UserResponse user = userService.getUserById(id);
        });
    }

}