package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    List<Restaurant> findAll();

    void deleteById(UUID id);

    List<Restaurant> searchRestaurant(String name, KitchenType kitchenType);

}
