package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindMenuItemByIdUseCaseTest {


    @Mock
    private MenuItemRepository menuItemRepository;

    private FindMenuItemByIdUseCase findMenuItemByIdUseCase;

    @BeforeEach
    void setUp() {
        findMenuItemByIdUseCase = new FindMenuItemByIdUseCase(menuItemRepository);
    }

    @Test
    void shouldReturnMenuItemWhenIdExists() {
        UUID id = UUID.randomUUID();
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
        MenuItem menuItem = MenuItem.create("Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant);

        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        MenuItem result = findMenuItemByIdUseCase.execute(id);

        assertEquals("Pizza", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                () -> findMenuItemByIdUseCase.execute(id));
    }

}