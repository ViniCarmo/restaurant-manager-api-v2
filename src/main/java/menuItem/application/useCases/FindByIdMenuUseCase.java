package menuItem.application.useCases;

import menuItem.domain.entity.MenuItem;
import menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindByIdMenuUseCase {

    private final MenuItemRepository menuItemRepository;

    public FindByIdMenuUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(UUID menuItemId) {
        return menuItemRepository.findById(menuItemId).orElseThrow(()
                -> new RuntimeException("Menu item not found"));
    }
}
