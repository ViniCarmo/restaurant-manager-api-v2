package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

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

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Restaurant execute(
            String name,
            String address,
            KitchenType kitchenType,
            LocalTime openingTime,
            LocalTime closingTime,
            UUID ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException(ownerId));

        if (!owner.isRestaurantOwner()) {
            throw new RestaurantOwnerRequiredException(
                    "User with id " + ownerId + " is not a restaurant owner");
        }

        Restaurant restaurant = Restaurant.create(
                name,
                address,
                kitchenType,
                openingTime,
                closingTime,
                owner
        );

        return restaurantRepository.save(restaurant);
    }
}
