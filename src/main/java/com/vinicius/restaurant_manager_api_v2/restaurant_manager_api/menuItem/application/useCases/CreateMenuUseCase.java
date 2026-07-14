package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantOwnerRequiredException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CreateMenuUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public CreateMenuUseCase(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public MenuItem execute(String name, String description, BigDecimal price, boolean dineInOnly, String photoPath, UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        UUID loggedUserId = authenticatedUserProvider.getLoggedUserId();
        User loggedUser = userRepository.findById(loggedUserId)
                .orElseThrow(() -> new UserNotFoundException(loggedUserId));

        if (!restaurant.belongsTo(loggedUser)) {
            throw new RestaurantAccessDeniedException(
                    "You are not the owner of this restaurant");
        }

        MenuItem menuItem = MenuItem.create(name, description, price, dineInOnly, photoPath, restaurant);
        return menuItemRepository.save(menuItem);
    }
}

