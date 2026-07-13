package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CreateMenuUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public CreateMenuUseCase(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuItem execute(String name, String description, BigDecimal price, boolean dineInOnly, String photoPath, UUID restaurantId){
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new MenuItemNotFoundException("Restaurant not found"));

        MenuItem menuItem = MenuItem.create(name, description, price, dineInOnly, photoPath, restaurant);
        return menuItemRepository.save(menuItem);
    }

    }

