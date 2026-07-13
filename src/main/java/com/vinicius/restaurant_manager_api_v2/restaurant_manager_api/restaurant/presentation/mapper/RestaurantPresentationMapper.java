package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.response.RestaurantResponseDto;

public class RestaurantPresentationMapper {
    public static RestaurantResponseDto toResponse(Restaurant restaurant) {

        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getKitchenType(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                restaurant.getRestaurantOwner().getId(),
                restaurant.getRestaurantOwner().getName(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

}
