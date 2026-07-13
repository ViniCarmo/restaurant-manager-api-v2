package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.entity.MenuItemJpaEntity;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemJpaEntity, UUID> {
    List<MenuItemJpaEntity> findByRestaurant(RestaurantJpaEntity restaurant);
}
