package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.UUID;

@Component
public class ListMenuItemsByRestaurantUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public ListMenuItemsByRestaurantUseCase(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<MenuItem> execute(UUID restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        return menuItemRepository.findByRestaurant(restaurant);
    }
}
