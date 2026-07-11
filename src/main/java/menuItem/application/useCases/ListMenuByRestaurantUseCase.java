package menuItem.application.useCases;

import menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Component
public class ListMenuByRestaurantUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public ListMenuByRestaurantUseCase(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void execute(UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()
                -> new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId));

    }
}
