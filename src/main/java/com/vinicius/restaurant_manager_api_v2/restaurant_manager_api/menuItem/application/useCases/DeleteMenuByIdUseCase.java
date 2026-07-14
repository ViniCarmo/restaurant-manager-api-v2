package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteMenuByIdUseCase {

    private final MenuItemRepository menuItemRepository;
    private final UserRepository  userRepository;
    private final RestaurantRepository restaurantRepository;
    private final AuthenticatedUserProvider  authenticatedUserProvider;

    public DeleteMenuByIdUseCase(MenuItemRepository menuItemRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }


    public void execute(UUID id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));

        Restaurant restaurant = restaurantRepository.findById(menuItem.getRestaurant().getId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + menuItem.getRestaurant().getId()));

        UUID loggedUserId = authenticatedUserProvider.getLoggedUserId();
        User loggedUser = userRepository.findById(loggedUserId)
                .orElseThrow(() -> new UserNotFoundException(loggedUserId));

        if (!restaurant.belongsTo(loggedUser)) {
            throw new RestaurantAccessDeniedException("You are not the owner of this restaurant's menu");
        }

        menuItemRepository.deleteById(id);
    }
}
