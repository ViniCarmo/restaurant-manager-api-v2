package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllMenuItemUseCaseTest {


    @Mock
    private MenuItemRepository menuItemRepository;

    private GetAllMenuItemUseCase getAllMenuItemUseCase;

    @BeforeEach
    void setUp() {
        getAllMenuItemUseCase = new GetAllMenuItemUseCase(menuItemRepository);
    }

    @Test
    void shouldReturnAllMenuItems() {
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
        MenuItem m1 = MenuItem.create("Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant);
        MenuItem m2 = MenuItem.create("Lasanha", "desc", new BigDecimal("39.90"), false, "/img2.jpg", restaurant);

        when(menuItemRepository.findAll()).thenReturn(List.of(m1, m2));

        List<MenuItem> result = getAllMenuItemUseCase.execute();

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoMenuItemsExist() {
        when(menuItemRepository.findAll()).thenReturn(List.of());

        List<MenuItem> result = getAllMenuItemUseCase.execute();

        assertTrue(result.isEmpty());
    }

}