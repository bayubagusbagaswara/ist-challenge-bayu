package com.ist.challenge.bayu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {


    @NotBlank(message = "Username must not be blank")
    @Size(max = 15, message = "Username length maximum must be 15 characters")
    private String username;
    @NotBlank(message = "Password must not be blank")
    @Size(max = 25, message = "Password length maximum must be 25 characters")
    private String password;
}
