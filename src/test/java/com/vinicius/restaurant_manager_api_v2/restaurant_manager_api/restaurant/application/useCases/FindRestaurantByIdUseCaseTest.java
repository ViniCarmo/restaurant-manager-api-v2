package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

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

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindRestaurantByIdUseCaseTest {


    @Mock
    private RestaurantRepository restaurantRepository;

    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @BeforeEach
    void setUp() {
        findRestaurantByIdUseCase = new FindRestaurantByIdUseCase(restaurantRepository);
    }

    @Test
    void shouldReturnRestaurantWhenIdExists() {
        UUID id = UUID.randomUUID();
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        Restaurant result = findRestaurantByIdUseCase.execute(id);

        assertEquals("Pizzaria Roma", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> findRestaurantByIdUseCase.execute(id));
    }

}