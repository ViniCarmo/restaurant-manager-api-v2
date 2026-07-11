package menuItem.application.useCases;

import menuItem.domain.entity.MenuItem;
import menuItem.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public GetAllMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> execute() {
        return menuItemRepository.findAll();
    }
}
