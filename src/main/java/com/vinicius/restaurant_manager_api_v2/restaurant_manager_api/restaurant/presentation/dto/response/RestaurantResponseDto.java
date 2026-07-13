package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.response;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record RestaurantResponseDto(UUID id,

                                    String name,

                                    String address,

                                    KitchenType kitchenType,

                                    LocalTime openingTime,

                                    LocalTime closingTime,

                                    UUID restaurantOwnerId,

                                    String restaurantOwnerName,

                                    LocalDateTime createdAt,

                                    LocalDateTime updatedAt) {
}
