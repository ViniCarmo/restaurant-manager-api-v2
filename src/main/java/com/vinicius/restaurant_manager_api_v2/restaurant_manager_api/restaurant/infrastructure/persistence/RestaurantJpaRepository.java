package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {
    Restaurant save(Restaurant restaurant);

    Optional<RestaurantJpaEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<RestaurantJpaEntity> findByNameContainingIgnoreCase(String name);

    void delete(Restaurant restaurant);


    List<RestaurantJpaEntity> findByKitchenType(KitchenType kitchenType);

    List<RestaurantJpaEntity> findByNameContainingIgnoreCaseAndKitchenType(
            String name,
            KitchenType kitchenType
    );
}
