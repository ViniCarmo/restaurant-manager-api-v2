package restaurant.application.useCases;

import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.enums.KitchenType;
import restaurant.domain.exception.RestaurantOwnerRequiredException;
import user.domain.entity.User;
import user.domain.Repository.UserRepository;
import user.domain.exceptions.UserNotFoundException;

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
