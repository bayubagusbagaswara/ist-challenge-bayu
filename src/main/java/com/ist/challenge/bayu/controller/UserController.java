package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.service.UserService;
import com.ist.challenge.bayu.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse user = userService.createUser(createUserRequest);
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.CREATED.value())
                .success(Boolean.TRUE)
                .message("Register berhasil dengan username : " + user.getUsername())
                .build();

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable(name = "id") Long id) {
        UserResponse user = userService.getUserById(id);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("User successfully retrieved based on id : " + user.getId())
                .data(user)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@RequestParam(name = "username") String username) {
        UserResponse user = userService.getUserByUsername(username);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("User successfully retrieved based on username")
                .data(user)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/username/like")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersUsernameLike(@RequestParam(name = "username") String username) {
        List<UserResponse> users = userService.getUsersByUsernameContains(username);
        ApiResponse<List<UserResponse>> apiResponse = ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("User successfully retrieved based on username")
                .data(users)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> allUsers = userService.getAllUsers();
        ApiResponse<List<UserResponse>> apiResponse = ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("All users successfully retrieved")
                .data(allUsers)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // get all user with parameter page and sort
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<ListUserResponse>> getAllUsersPage(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        ListUserRequest listUserRequest = ListUserRequest.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        ListUserResponse allUsersParam = userService.listAllUsers(listUserRequest);
        ApiResponse<ListUserResponse> apiResponse = ApiResponse.<ListUserResponse>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("All users successfully retrieved")
                .data(allUsersParam)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // update user
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUser(@PathVariable(name = "id") Long id,
                                                                      @RequestBody UpdateUserRequest updateUserRequest) {

        UpdateUserResponse user = userService.updateUser(id, updateUserRequest);
        ApiResponse<UpdateUserResponse> apiResponse = ApiResponse.<UpdateUserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .success(Boolean.TRUE)
                .message("Success update user by id : " + id)
                .data(user)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUserById(id);
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("Successfully deleted user by id : " + id)
                .build();

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
