package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;

import java.util.UUID;

@Component
public class DeleteByIdUseCase {

    private final MenuItemRepository menuItemRepository;

    public DeleteByIdUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    public void execute(UUID id) {
        menuItemRepository.findById(id).orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));
        menuItemRepository.deleteById(id);

    }
}
