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
class GetAllRestaurantsUseCaseTest {


    @Mock
    private RestaurantRepository restaurantRepository;

    private GetAllRestaurantsUseCase getAllRestaurantsUseCase;

    @BeforeEach
    void setUp() {
        getAllRestaurantsUseCase = new GetAllRestaurantsUseCase(restaurantRepository);
    }

    @Test
    void shouldReturnAllRestaurants() {
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant r1 = Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
        Restaurant r2 = Restaurant.create("Sushi Bar", "Rua B, 456", KitchenType.JAPANESE,
                LocalTime.of(12, 0), LocalTime.of(22, 0), owner);

        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Restaurant> result = getAllRestaurantsUseCase.execute();

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoRestaurantsExist() {
        when(restaurantRepository.findAll()).thenReturn(List.of());

        List<Restaurant> result = getAllRestaurantsUseCase.execute();

        assertTrue(result.isEmpty());
    }

}