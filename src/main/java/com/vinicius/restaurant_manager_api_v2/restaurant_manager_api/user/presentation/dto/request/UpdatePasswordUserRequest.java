package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordUserRequest(@NotBlank(message = "Password is required")
                                        String password) {
}
