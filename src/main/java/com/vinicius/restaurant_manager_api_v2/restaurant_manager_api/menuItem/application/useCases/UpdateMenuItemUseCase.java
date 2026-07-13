package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UpdateMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public UpdateMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(UUID id, String name, String description, BigDecimal price, boolean dineInOnly, String photoPath) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("MenuItem not found with id: " + id));

        menuItem.changeName(name);
        menuItem.changeDescription(description);
        menuItem.changePrice(price);
        menuItem.changePhotoPath(photoPath);
        menuItem.changeDineInOnly(dineInOnly);

        return menuItemRepository.save(menuItem);
    }
}
