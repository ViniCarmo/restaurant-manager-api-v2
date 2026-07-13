package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(@NotBlank(message = "Name is required")
                                 String name,

                                @Email(message = "Invalid email")
                                 @NotBlank(message = "Email is required")
                                 String email) {
}
