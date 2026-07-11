package restaurant.application.useCases;

import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.util.UUID;

@Component
public class DeleteRestaurantById {

    private final RestaurantRepository restaurantRepository;

    public DeleteRestaurantById(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void execute(UUID id){
        restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));
        restaurantRepository.deleteById(id);
    }
}
