package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.entity.RestaurantJpaEntity;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.persistence.mapper.RestaurantMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository repository;

    public RestaurantRepositoryImpl(RestaurantJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return RestaurantMapper.toDomainEntity(
                repository.save(RestaurantMapper.toJpaEntity(restaurant))
        );
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {
        return repository.findById(id)
                .map(RestaurantMapper::toDomainEntity);
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll()
                .stream()
                .map(RestaurantMapper::toDomainEntity)
                .toList();
    }

    @Override
    public Optional<Restaurant> findByNameIgnoreCase(String name) {
        return repository.findByNameIgnoreCase(name)
                .map(RestaurantMapper::toDomainEntity);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }


    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Restaurant> searchRestaurant(String name, KitchenType kitchenType) {
        List<RestaurantJpaEntity> restaurants;

        if (name != null && !name.isBlank() && kitchenType != null) {
            restaurants = repository.findByNameContainingIgnoreCaseAndKitchenType(name, kitchenType);

        } else if (name != null && !name.isBlank()) {
            restaurants = repository.findByNameContainingIgnoreCase(name);

        } else if (kitchenType != null) {
            restaurants = repository.findByKitchenType(kitchenType);

        } else {
            restaurants = repository.findAll();
        }

        return restaurants.stream()
                .map(RestaurantMapper::toDomainEntity)
                .toList();
    }
}


