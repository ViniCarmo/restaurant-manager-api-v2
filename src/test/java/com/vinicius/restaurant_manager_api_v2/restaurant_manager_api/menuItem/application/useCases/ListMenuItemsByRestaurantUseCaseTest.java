package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListMenuItemsByRestaurantUseCaseTest {


    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    private ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;

    @BeforeEach
    void setUp() {
        listMenuItemsByRestaurantUseCase = new ListMenuItemsByRestaurantUseCase(menuItemRepository, restaurantRepository);
    }

    @Test
    void shouldReturnMenuItemsWhenRestaurantExists() {
        UUID restaurantId = UUID.randomUUID();
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
        MenuItem menuItem = MenuItem.create("Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.findByRestaurant(restaurant)).thenReturn(List.of(menuItem));

        List<MenuItem> result = listMenuItemsByRestaurantUseCase.execute(restaurantId);

        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> listMenuItemsByRestaurantUseCase.execute(restaurantId));
    }

}