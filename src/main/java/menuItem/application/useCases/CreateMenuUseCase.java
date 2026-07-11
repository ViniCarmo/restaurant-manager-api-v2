package menuItem.application.useCases;

import menuItem.domain.entity.MenuItem;
import menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.awt.*;
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
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        MenuItem menuItem = MenuItem.create(name, description, price, dineInOnly, photoPath, restaurant);
        return menuItemRepository.save(menuItem);
    }

    }

