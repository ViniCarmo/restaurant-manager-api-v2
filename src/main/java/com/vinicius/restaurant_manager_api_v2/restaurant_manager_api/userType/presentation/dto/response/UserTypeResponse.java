package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.response;

import java.util.UUID;

public record UserTypeResponse(
        UUID id,
        String name) {
}
