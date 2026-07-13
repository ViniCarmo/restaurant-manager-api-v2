package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.request;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record UpdateRestaurantrequestDto(@NotBlank(message = "Name is required")
                                          String name,

                                         @NotBlank(message = "Address is required")
                                          String address,

                                         @NotNull(message = "Kitchen type is required")
                                         KitchenType kitchenType,

                                         @NotNull(message = "Opening time is required")
                                         LocalTime openingTime,

                                         @NotNull(message = "Closing time is required")
                                          LocalTime closingTime) {
}
