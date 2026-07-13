package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.infrastructure.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.entity.MenuItemJpaEntity;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.persistence.mapper.RestaurantMapper;

public class MenuItemMapper {
    public static MenuItemJpaEntity toJpaEntity(MenuItem menuItem) {

        if (menuItem == null) {
            return null;
        }

        return new MenuItemJpaEntity(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isDineInOnly(),
                menuItem.getPhotoPath(),
                RestaurantMapper.toJpaEntity(menuItem.getRestaurant()),
                menuItem.getCreatedAt(),
                menuItem.getUpdatedAt()
        );
    }

    public static MenuItem toDomainEntity(MenuItemJpaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new MenuItem(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.isDineInOnly(),
                entity.getPhotoPath(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                RestaurantMapper.toDomainEntity(entity.getRestaurant())
        );
    }
}
