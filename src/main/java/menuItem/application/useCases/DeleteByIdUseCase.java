package menuItem.application.useCases;

import menuItem.domain.repository.MenuItemRepository;
import restaurant.domain.Repository.RestaurantRepository;
import restaurant.domain.exception.RestaurantNotFoundException;

import java.util.UUID;

public class DeleteByIdUseCase {

    private final MenuItemRepository menuItemRepository;

    public DeleteByIdUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    public void execute(UUID id) {
        menuItemRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));
        menuItemRepository.deleteById(id);

    }
}
