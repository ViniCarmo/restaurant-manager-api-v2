package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(UUID id,
                              String name,
                              String email,
                              String userType,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt
                              ) {
}
