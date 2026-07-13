package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRequestDto(@NotBlank(message = "Name is required")
                              String name,

                             @Email(message = "Invalid email")
                              @NotBlank(message = "Email is required")
                              String email,

                             @NotBlank(message = "Password is required")
                              String password,

                             @NotNull(message = "User type is required")
                             UUID userTypeId) {
}
