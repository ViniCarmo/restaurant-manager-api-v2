package restaurant.application.useCases;

import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.util.UUID;

@Component
public class RestaurantFindByIdUseCase {
  private final RestaurantRepository restaurantRepository;

    public RestaurantFindByIdUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant execute(UUID id){
      return restaurantRepository.findById(id).orElseThrow(()
              -> new RestaurantNotFoundException("Restaurant not found with id: " + id));
  }

}
