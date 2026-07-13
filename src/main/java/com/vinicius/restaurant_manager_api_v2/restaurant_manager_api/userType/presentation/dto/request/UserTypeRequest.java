package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserTypeRequest(@NotBlank(message = "Name is required")
                                    String name) {
}
