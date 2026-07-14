package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.repository.MenuItemRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseTest {


    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemRepository, userRepository, restaurantRepository, authenticatedUserProvider);
    }

    private User owner() {
        return User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("restaurant owner"));
    }

    private Restaurant restaurantOf(User owner) {
        return Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
    }

    @Test
    void shouldUpdateMenuItemWhenLoggedUserIsOwner() {
        UUID menuItemId = UUID.randomUUID();
        User owner = owner();
        Restaurant restaurant = restaurantOf(owner);
        MenuItem menuItem = MenuItem.create("Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItem));
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(owner.getId());
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = updateMenuItemUseCase.execute(
                menuItemId, "Pizza Especial", "Nova desc", new BigDecimal("55.00"), true, "/novo.jpg");

        assertEquals("Pizza Especial", result.getName());
        assertEquals(new BigDecimal("55.00"), result.getPrice());
        assertTrue(result.isDineInOnly());
    }

    @Test
    void shouldThrowExceptionWhenMenuItemDoesNotExist() {
        UUID menuItemId = UUID.randomUUID();
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                () -> updateMenuItemUseCase.execute(menuItemId, "Nome", "Desc",
                        new BigDecimal("10"), false, "/img.jpg"));

        verify(menuItemRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenLoggedUserIsNotOwner() {
        UUID menuItemId = UUID.randomUUID();
        User owner = owner();
        User anotherUser = User.create("Outro", "outro@email.com", "123456", UserType.create("restaurant owner"));
        Restaurant restaurant = restaurantOf(owner);
        MenuItem menuItem = MenuItem.create("Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItem));
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(anotherUser.getId());
        when(userRepository.findById(anotherUser.getId())).thenReturn(Optional.of(anotherUser));

        assertThrows(RestaurantAccessDeniedException.class,
                () -> updateMenuItemUseCase.execute(menuItemId, "Nome", "Desc",
                        new BigDecimal("10"), false, "/img.jpg"));

        verify(menuItemRepository, never()).save(any());
    }

}