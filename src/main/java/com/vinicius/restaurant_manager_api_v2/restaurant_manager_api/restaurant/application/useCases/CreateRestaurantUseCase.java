package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantOwnerRequiredException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;

import java.time.LocalTime;
import java.util.UUID;

@Component
public class CreateRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository, UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public Restaurant execute(
            String name,
            String address,
            KitchenType kitchenType,
            LocalTime openingTime,
            LocalTime closingTime,
            UUID ownerId) {
        UUID loggedUserId = authenticatedUserProvider.getLoggedUserId();
        User loggedUser = userRepository.findById(loggedUserId)
                .orElseThrow(() -> new UserNotFoundException(loggedUserId));

        if (!loggedUser.isRestaurantOwner()) {
            throw new RestaurantOwnerRequiredException(
                    "User with id " + loggedUserId + " is not a restaurant owner");
        }

        Restaurant restaurant = Restaurant.create(
                name,
                address,
                kitchenType,
                openingTime,
                closingTime,
                loggedUser
        );

        return restaurantRepository.save(restaurant);
    }
}
