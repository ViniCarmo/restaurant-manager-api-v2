package menuItem.domain.repository;

import menuItem.domain.entity.MenuItem;
import restaurant.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);

    Optional<MenuItem> findById(UUID id);

    List<MenuItem> findAll();

    List<MenuItem> findByRestaurant(Restaurant restaurant);

    void deleteById(UUID id);
}
