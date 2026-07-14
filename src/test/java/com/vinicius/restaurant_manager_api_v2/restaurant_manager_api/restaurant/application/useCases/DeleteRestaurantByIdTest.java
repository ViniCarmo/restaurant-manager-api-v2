package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantByIdTest {


    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private DeleteRestaurantById deleteRestaurantById;

    @BeforeEach
    void setUp() {
        deleteRestaurantById = new DeleteRestaurantById(restaurantRepository, userRepository, authenticatedUserProvider);
    }

    private User owner() {
        return User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
    }

    private Restaurant restaurantOf(User owner) {
        return Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
    }

    @Test
    void shouldDeleteRestaurantWhenLoggedUserIsOwner() {
        UUID restaurantId = UUID.randomUUID();
        User owner = owner();
        Restaurant restaurant = restaurantOf(owner);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(owner.getId());
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        deleteRestaurantById.execute(restaurantId);

        verify(restaurantRepository).deleteById(restaurantId);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> deleteRestaurantById.execute(restaurantId));

        verify(restaurantRepository, never()).deleteById(any());
    }

    @Test
    void shouldThrowExceptionWhenLoggedUserIsNotOwner() {
        UUID restaurantId = UUID.randomUUID();
        User owner = owner();
        User anotherUser = User.create("Outro", "outro@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = restaurantOf(owner);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(anotherUser.getId());
        when(userRepository.findById(anotherUser.getId())).thenReturn(Optional.of(anotherUser));

        assertThrows(RestaurantAccessDeniedException.class,
                () -> deleteRestaurantById.execute(restaurantId));

        verify(restaurantRepository, never()).deleteById(any());
    }

}