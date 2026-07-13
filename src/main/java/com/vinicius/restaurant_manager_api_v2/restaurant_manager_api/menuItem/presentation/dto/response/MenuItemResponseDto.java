package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MenuItemResponseDto(UUID id,

                                  String name,

                                  String description,

                                  BigDecimal price,

                                  boolean dineInOnly,

                                  String photoPath,

                                  UUID restaurantId,

                                  String restaurantName,

                                  LocalDateTime createdAt,

                                  LocalDateTime updatedAt) {
}
