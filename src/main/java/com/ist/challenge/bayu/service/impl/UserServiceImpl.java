package com.ist.challenge.bayu.service.impl;

import com.ist.challenge.bayu.dto.*;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ConflictException;
import com.ist.challenge.bayu.exception.ResourceNotFoundException;
import com.ist.challenge.bayu.model.User;
import com.ist.challenge.bayu.repository.UserRepository;
import com.ist.challenge.bayu.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        checkUsernameIsExists(createUserRequest.getUsername());

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());

        userRepository.save(user);

        return CreateUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = findById(id);
        return mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException( "User not found with username : " + username));
        return mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByUsernameContains(String username) {
        List<User> userList = userRepository.findByUsernameContainingIgnoreCase(username);
        return mapToUserResponseList(userList);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapToUserResponseList(users);
    }

    @Override
    public ListUserResponse listAllUsers(ListUserRequest listUserRequest) {
        int pageNo = listUserRequest.getPageNo();
        int pageSize = listUserRequest.getPageSize();
        String sortBy = listUserRequest.getSortBy();
        String sortDir = listUserRequest.getSortDir();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();

        List<UserResponse> userResponses = mapToUserResponseList(userList);

        return ListUserResponse.builder()
                .users(userResponses)
                .pageNo(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = findById(id);

        // check username baru apakah sudah ada di database
        checkUsernameIsExists(updateUserRequest.getUsername());

        // check password baru tidak boleh sama dengan password lama
        if (user.getPassword().equalsIgnoreCase(updateUserRequest.getPassword())) {
            throw new BadRequestException("Password tidak boleh sama dengan password sebelumnya");
        }

        // update User
        if (updateUserRequest.getUsername() != null) {
            user.setUsername(updateUserRequest.getUsername());
        }

        if (updateUserRequest.getPassword() != null) {
            user.setPassword(updateUserRequest.getPassword());
        }

        userRepository.save(user);

        return UpdateUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public void deleteUserById(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
    }

    public void checkUsernameIsExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("Username sudah terpakai");
        }
    }

    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }

    public List<UserResponse> mapToUserResponseList(List<User> users) {
        return users
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

}
