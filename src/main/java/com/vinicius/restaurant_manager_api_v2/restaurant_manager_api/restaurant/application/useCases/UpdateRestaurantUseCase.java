package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;

import java.time.LocalTime;
import java.util.UUID;

@Component
public class UpdateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository  userRepository;
    private final AuthenticatedUserProvider  authenticatedUserProvider;

    public UpdateRestaurantUseCase(RestaurantRepository restaurantRepository, UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public Restaurant execute(UUID id, String name, String address, LocalTime openingTime, LocalTime closingTime) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        UUID loggedUserId = authenticatedUserProvider.getLoggedUserId();
        User loggedUser = userRepository.findById(loggedUserId)
                .orElseThrow(() -> new UserNotFoundException(loggedUserId));

        if (!restaurant.belongsTo(loggedUser)) {
            throw new RestaurantAccessDeniedException("You are not the owner of this restaurant");
        }


        restaurant.changeName(name);
        restaurant.changeAddress(address);
        restaurant.changeOpeningHours(openingTime, closingTime);

        return restaurantRepository.save(restaurant);
    }
}
