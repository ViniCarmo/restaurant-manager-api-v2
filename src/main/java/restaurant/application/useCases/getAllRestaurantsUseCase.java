package restaurant.application.useCases;

import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;

import java.util.List;

@Component
public class getAllRestaurantsUseCase {

    private final RestaurantRepository restaurantRepository;

    public getAllRestaurantsUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> execute() {
        return restaurantRepository.findAll();
    }
}

