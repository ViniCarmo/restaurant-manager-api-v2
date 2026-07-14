package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    private SearchRestaurantUseCase searchRestaurantUseCase;

    @BeforeEach
    void setUp() {
        searchRestaurantUseCase = new SearchRestaurantUseCase(restaurantRepository);
    }

    @Test
    void shouldReturnRestaurantsMatchingNameAndKitchenType() {
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);

        when(restaurantRepository.searchRestaurant("Roma", KitchenType.ITALIAN))
                .thenReturn(List.of(restaurant));

        List<Restaurant> result = searchRestaurantUseCase.execute("Roma", KitchenType.ITALIAN);

        assertEquals(1, result.size());
        assertEquals("Pizzaria Roma", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoMatchFound() {
        when(restaurantRepository.searchRestaurant("Inexistente", null))
                .thenReturn(List.of());

        List<Restaurant> result = searchRestaurantUseCase.execute("Inexistente", null);

        assertTrue(result.isEmpty());
    }

}