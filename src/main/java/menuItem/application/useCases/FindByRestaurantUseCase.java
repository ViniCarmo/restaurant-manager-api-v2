package menuItem.application.useCases;

import menuItem.domain.entity.MenuItem;
import menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.entity.Restaurant;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.UUID;

@Component
public class FindByRestaurantUseCase {

    private final MenuItemRepository   menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public FindByRestaurantUseCase(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<MenuItem> execute(UUID restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        return menuItemRepository.findByRestaurant(restaurant);
    }
}
