package restaurant.application.useCases;

import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.enums.KitchenType;

import java.util.List;

@Component
public class SearchRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;

    public SearchRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> searchRestaurant(String name,
                                             KitchenType kitchenType){

        return restaurantRepository.searchRestaurant(name, kitchenType);
    }

}
