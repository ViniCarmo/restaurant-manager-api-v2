package restaurant.application.useCases;

import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.time.LocalTime;
import java.util.UUID;

public class UpdateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;

    public UpdateRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant execute(UUID id, String name, String address, LocalTime openingTime, LocalTime closingTime) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        restaurant.changeName(name);
        restaurant.changeAddress(address);
        restaurant.changeOpeningHours(openingTime, closingTime);

        return restaurantRepository.save(restaurant);
    }
}
