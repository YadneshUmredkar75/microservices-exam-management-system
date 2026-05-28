package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminRequest {

    @NotNull(message = "Auth user ID is required")
    private Long authUserId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    private Long departmentId;
}