package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.infrastructure.entity.MenuItemJpaEntity;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.infrastructure.mapper.MenuItemMapper;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.persistence.mapper.RestaurantMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final MenuItemJpaRepository repository;

    public MenuItemRepositoryImpl(MenuItemJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        return MenuItemMapper.toDomainEntity(
                repository.save(MenuItemMapper.toJpaEntity(menuItem))
        );
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return repository.findById(id)
                .map(MenuItemMapper::toDomainEntity);
    }

    @Override
    public List<MenuItem> findAll() {
        return repository.findAll()
                .stream()
                .map(MenuItemMapper::toDomainEntity)
                .toList();
    }

    @Override
    public List<MenuItem> findByRestaurant(Restaurant restaurant) {

        List<MenuItemJpaEntity> items = repository.findByRestaurant(
                RestaurantMapper.toJpaEntity(restaurant)
        );

        return items.stream()
                .map(MenuItemMapper::toDomainEntity)
                .toList();
    }

    @Override
    public void delete(MenuItem menuItem) {
        repository.deleteById(MenuItemMapper.toJpaEntity(menuItem).getId());
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
