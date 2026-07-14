package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantValidationException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private User restaurantOwner() {
        UserType type = UserType.create("restaurant owner");
        return User.create("Vinicius", "vinicius@email.com", "123456", type);
    }

    private User anotherUser() {
        UserType type = UserType.create("restaurant owner");
        return User.create("Outro Dono", "outro@email.com", "123456", type);
    }

    @Test
    void shouldCreateRestaurantWithValidData() {
        User owner = restaurantOwner();

        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma",
                "Rua A, 123",
                KitchenType.ITALIAN,
                LocalTime.of(18, 0),
                LocalTime.of(23, 0),
                owner
        );

        assertNotNull(restaurant.getId());
        assertEquals("Pizzaria Roma", restaurant.getName());
        assertEquals("Rua A, 123", restaurant.getAddress());
        assertEquals(KitchenType.ITALIAN, restaurant.getKitchenType());
        assertEquals(owner, restaurant.getRestaurantOwner());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        User owner = restaurantOwner();

        assertThrows(RestaurantValidationException.class,
                () -> Restaurant.create("", "Rua A, 123", KitchenType.ITALIAN,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), owner));
    }

    @Test
    void shouldThrowExceptionWhenAddressIsBlank() {
        User owner = restaurantOwner();

        assertThrows(RestaurantValidationException.class,
                () -> Restaurant.create("Pizzaria Roma", "", KitchenType.ITALIAN,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), owner));
    }

    @Test
    void shouldThrowExceptionWhenKitchenTypeIsNull() {
        User owner = restaurantOwner();

        assertThrows(RestaurantValidationException.class,
                () -> Restaurant.create("Pizzaria Roma", "Rua A, 123", null,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), owner));
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsNull() {
        assertThrows(RestaurantValidationException.class,
                () -> Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), null));
    }

    @Test
    void shouldThrowExceptionWhenOpeningTimeIsAfterClosingTime() {
        User owner = restaurantOwner();

        assertThrows(RestaurantValidationException.class,
                () -> Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                        LocalTime.of(23, 0), LocalTime.of(18, 0), owner));
    }

    @Test
    void shouldThrowExceptionWhenOpeningTimeIsNull() {
        User owner = restaurantOwner();

        assertThrows(RestaurantValidationException.class,
                () -> Restaurant.create("Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                        null, LocalTime.of(18, 0), owner));
    }

    @Test
    void shouldReturnTrueWhenRestaurantIsOpen() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        assertTrue(restaurant.isOpen(LocalTime.of(20, 0)));
    }

    @Test
    void shouldReturnFalseWhenRestaurantIsClosed() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        assertFalse(restaurant.isOpen(LocalTime.of(10, 0)));
    }

    @Test
    void shouldReturnTrueWhenTimeEqualsOpeningTime() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        assertTrue(restaurant.isOpen(LocalTime.of(18, 0)));
    }

    @Test
    void shouldReturnTrueWhenTimeEqualsClosingTime() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        assertTrue(restaurant.isOpen(LocalTime.of(23, 0)));
    }

    @Test
    void shouldReturnTrueWhenUserBelongsToRestaurant() {
        User owner = restaurantOwner();
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);

        assertTrue(restaurant.belongsTo(owner));
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotBelongToRestaurant() {
        User owner = restaurantOwner();
        User other = anotherUser();
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);

        assertFalse(restaurant.belongsTo(other));
    }

    @Test
    void shouldChangeNameCorrectly() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        restaurant.changeName("Pizzaria Napoli");

        assertEquals("Pizzaria Napoli", restaurant.getName());
    }

    @Test
    void shouldChangeAddressCorrectly() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        restaurant.changeAddress("Rua B, 456");

        assertEquals("Rua B, 456", restaurant.getAddress());
    }

    @Test
    void shouldChangeOpeningHoursCorrectly() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        restaurant.changeOpeningHours(LocalTime.of(17, 0), LocalTime.of(22, 0));

        assertEquals(LocalTime.of(17, 0), restaurant.getOpeningTime());
        assertEquals(LocalTime.of(22, 0), restaurant.getClosingTime());
    }

    @Test
    void shouldThrowExceptionWhenChangingToInvalidOpeningHours() {
        Restaurant restaurant = Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner());

        assertThrows(RestaurantValidationException.class,
                () -> restaurant.changeOpeningHours(LocalTime.of(23, 0), LocalTime.of(18, 0)));
    }

}