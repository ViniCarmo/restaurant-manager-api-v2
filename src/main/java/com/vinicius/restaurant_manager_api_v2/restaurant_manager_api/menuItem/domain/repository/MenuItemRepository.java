package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);

    Optional<MenuItem> findById(UUID id);

    List<MenuItem> findAll();

    List<MenuItem> findByRestaurant(Restaurant restaurant);

    void delete(MenuItem menuItem);

    void deleteById(UUID id);
}
