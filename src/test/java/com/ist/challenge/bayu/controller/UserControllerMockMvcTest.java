package com.ist.challenge.bayu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ConflictException;
import com.ist.challenge.bayu.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerMockMvcTest {

    private final static Logger log = LoggerFactory.getLogger(UserControllerMockMvcTest.class);

    @Autowired
    MockMvc mockMvc;

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

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @Order(1)
    void createUserSuccess() throws Exception {
        createUserRequest = new CreateUserRequest("bayu", "bayu123");
        createUserResponse = new CreateUserResponse(1L, "bayu", "bayu123");

        when(userService.createUser(createUserRequest))
                .thenReturn(createUserResponse);

        String jsonBody = mapper.writeValueAsString(createUserRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users/save")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(print());
    }

    @Test
    @Order(2)
    void createUserFailedUsernameIsExists() throws Exception {
        createUserRequest = new CreateUserRequest("bayu", "bayu123");
        createUserResponse = new CreateUserResponse(1L, "bayu", "bayu123");


        CreateUserRequest createUserRequest1 = new CreateUserRequest("bayu", "bayu12345");

        when(userService.createUser(createUserRequest))
                .thenReturn(createUserResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users/save")
                        .content(mapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(print());

        when(userService.createUser(createUserRequest1))
                .thenThrow(ConflictException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users/save")
                        .content(mapper.writeValueAsString(createUserRequest1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @Order(3)
    void getUserById() throws Exception {
        userResponse = new UserResponse(1L, "bayu", "bayu123");
        Long userId = 1L;

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value(1))
                .andExpect(jsonPath(".username").value("bayu"))
                .andExpect(jsonPath(".password").value("bayu123"))
                .andDo(print());
    }

    @Test
    @Order(4)
    void getUserByUsername() throws Exception {
        String username = "bayu";
        userResponse = new UserResponse(1L, "bayu", "bayu123");

        when(userService.getUserByUsername(username))
                .thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/username")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value(1))
                .andExpect(jsonPath(".username").value("bayu"))
                .andExpect(jsonPath(".password").value("bayu123"))
                .andDo(print());
    }

    @Test
    @Order(5)
    void getUsersUsernameLike() throws Exception {
        String username = "ba";
        userResponseList = new ArrayList<>();
        userResponseList.add(new UserResponse(1L, "bayu", "bayu123"));
        userResponseList.add(new UserResponse(2L, "bagus", "bagus123"));

        when(userService.getUsersByUsernameContains(username))
                .thenReturn(userResponseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/username/like")
                        .param("username", username))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data[0].username", Matchers.equalTo("bayu")))
                .andExpect(jsonPath("$.data[1].username", Matchers.equalTo("bagus")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    void getAllUsers() throws Exception {

        userResponseList = new ArrayList<>();
        userResponseList.add(new UserResponse(1L, "bayu", "bayu123"));
        userResponseList.add(new UserResponse(2L, "bagus", "bagus123"));

        when(userService.getAllUsers())
                .thenReturn(userResponseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(7)
    void getAllUsersPage() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/page")
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy)
                        .param("sortDir", sortDir))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.users", Matchers.hasSize(3)))
                .andDo(print());
    }

    @Test
    @Order(8)
    void updateUser() throws Exception {
        userResponse = new UserResponse(1L, "bayu", "bayu123");

        updateUserRequest = new UpdateUserRequest("bagus", "bagus123");
        Long userId = 1L;

        updateUserResponse = new UpdateUserResponse(1L, "bagus", "bagus123");

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        when(userService.updateUser(userId, updateUserRequest))
                .thenReturn(updateUserResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/users/{id}", userId)
                        .content(mapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.data.username").value(updateUserRequest.getUsername()))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(9)
    void updateUserErrorUsernameIsExists() throws Exception {
        // save dulu
        createUserRequest = new CreateUserRequest("bayu", "bayu123");
        createUserResponse = new CreateUserResponse(1L, "bayu", "bayu123");
        when(userService.createUser(createUserRequest))
                .thenReturn(createUserResponse);
        String jsonBody = mapper.writeValueAsString(createUserRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users/save")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(print());

        userResponse = new UserResponse(2L, "bagus", "bagus123");

        updateUserRequest = new UpdateUserRequest("bayu", "bagus12345");
        Long userId = 2L;
        updateUserResponse = new UpdateUserResponse(2L, "bagus", "bagus123");

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        // lakukan update tetapi gagal
        when(userService.updateUser(userId, updateUserRequest))
                .thenThrow(ConflictException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/users/{id}", userId)
                        .content(mapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @Order(10)
    void updateUserErrorNewPasswordIsTheSameAsTheOldOne() throws Exception {
        userResponse = new UserResponse(2L, "bagus", "bagus123");

        Long userId = 2L;
        updateUserRequest = new UpdateUserRequest("albert", "bagus123");

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        // update gagal
        when(userService.updateUser(userId, updateUserRequest))
                .thenThrow(BadRequestException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/users/{id}", userId)
                        .content(mapper.writeValueAsString(updateUserRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(11)
    void deleteUser() throws Exception {
        userResponse = new UserResponse(1L, "bayu", "bayu123");
        Long userId = 1L;

        when(userService.getUserById(userId))
                .thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(print());
    }
}