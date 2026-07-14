package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantOwnerRequiredException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {


    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private CreateRestaurantUseCase createRestaurantUseCase;

    @BeforeEach
    void setUp() {
        createRestaurantUseCase = new CreateRestaurantUseCase(restaurantRepository, userRepository, authenticatedUserProvider);
    }

    @Test
    void shouldCreateRestaurantWhenLoggedUserIsRestaurantOwner() {
        UUID loggedUserId = UUID.randomUUID();
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));

        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(loggedUserId);
        when(userRepository.findById(loggedUserId)).thenReturn(Optional.of(owner));
        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant result = createRestaurantUseCase.execute(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), loggedUserId);

        assertNotNull(result);
        assertEquals("Pizzaria Roma", result.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void shouldThrowExceptionWhenLoggedUserDoesNotExist() {
        UUID loggedUserId = UUID.randomUUID();
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(loggedUserId);
        when(userRepository.findById(loggedUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> createRestaurantUseCase.execute(
                        "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), loggedUserId));

        verify(restaurantRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenLoggedUserIsNotRestaurantOwner() {
        UUID loggedUserId = UUID.randomUUID();
        User customer = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("customer"));

        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(loggedUserId);
        when(userRepository.findById(loggedUserId)).thenReturn(Optional.of(customer));

        assertThrows(RestaurantOwnerRequiredException.class,
                () -> createRestaurantUseCase.execute(
                        "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), loggedUserId));

        verify(restaurantRepository, never()).save(any());
    }

}