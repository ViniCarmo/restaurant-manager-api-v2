package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.persistence.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.entity.RestaurantJpaEntity;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.persistence.mapper.UserMapper;

public class RestaurantMapper {

    public static RestaurantJpaEntity toJpaEntity(Restaurant restaurant) {

        if (restaurant == null) {
            return null;
        }

        return new RestaurantJpaEntity(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getKitchenType(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                UserMapper.toJpaEntity(restaurant.getRestaurantOwner()),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

    public static Restaurant toDomainEntity(RestaurantJpaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getKitchenType(),
                entity.getOpeningTime(),
                entity.getClosingTime(),
                UserMapper.toDomainEntity(entity.getRestaurantOwner()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
