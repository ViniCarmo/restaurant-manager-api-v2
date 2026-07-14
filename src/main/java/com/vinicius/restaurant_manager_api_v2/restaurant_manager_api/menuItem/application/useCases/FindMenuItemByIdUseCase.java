package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindMenuItemByIdUseCase {

    private final MenuItemRepository menuItemRepository;

    public FindMenuItemByIdUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(UUID menuItemId) {
        return menuItemRepository.findById(menuItemId).orElseThrow(()
                -> new MenuItemNotFoundException("Menu item not found"));
    }
}
