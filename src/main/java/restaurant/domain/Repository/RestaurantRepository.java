package restaurant.domain.Repository;

import restaurant.domain.entity.Restaurant;
import restaurant.domain.enums.KitchenType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    List<Restaurant> findAll();

    void deleteById(UUID id);

    List<Restaurant> searchRestaurant(String name, KitchenType kitchenType);

}
