package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateMenuItemrequestDto(@NotBlank(message = "Name is required")
                                       String name,

                                       String description,

                                       @NotNull(message = "Price is required")
                                       @Positive(message = "Price must be greater than zero")
                                       BigDecimal price,

                                       boolean dineInOnly,

                                       String photoPath) {
}
