package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create new user

    // get user by id

    // get all user

    // get all user by pagination and sorting

    // update user

    // delete user
}
