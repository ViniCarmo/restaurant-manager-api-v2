package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemValidationException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    private Restaurant restaurant() {
        UserType type = UserType.create("restaurant owner");
        User owner = User.create("Vinicius", "vinicius@email.com", "123456", type);
        return Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), owner);
    }

    @Test
    void shouldCreateMenuItemWithValidData() {
        MenuItem menuItem = MenuItem.create(
                "Pizza Margherita", "Molho, mussarela e manjericão",
                new BigDecimal("45.90"), false, "/images/pizza.jpg", restaurant());

        assertNotNull(menuItem.getId());
        assertEquals("Pizza Margherita", menuItem.getName());
        assertEquals("Molho, mussarela e manjericão", menuItem.getDescription());
        assertEquals(new BigDecimal("45.90"), menuItem.getPrice());
        assertFalse(menuItem.isDineInOnly());
        assertEquals("/images/pizza.jpg", menuItem.getPhotoPath());
        assertNotNull(menuItem.getRestaurant());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(MenuItemValidationException.class,
                () -> MenuItem.create("", "desc", new BigDecimal("10"), false, "/img.jpg", restaurant()));
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(MenuItemValidationException.class,
                () -> MenuItem.create(null, "desc", new BigDecimal("10"), false, "/img.jpg", restaurant()));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        assertThrows(MenuItemValidationException.class,
                () -> MenuItem.create("Pizza", "desc", null, false, "/img.jpg", restaurant()));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsZero() {
        assertThrows(MenuItemValidationException.class,
                () -> MenuItem.create("Pizza", "desc", BigDecimal.ZERO, false, "/img.jpg", restaurant()));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThrows(MenuItemValidationException.class,
                () -> MenuItem.create("Pizza", "desc", new BigDecimal("-5"), false, "/img.jpg", restaurant()));
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIsNull() {
        assertThrows(MenuItemValidationException.class,
                () -> MenuItem.create("Pizza", "desc", new BigDecimal("10"), false, "/img.jpg", null));
    }

    @Test
    void shouldChangePriceCorrectly() {
        MenuItem menuItem = MenuItem.create(
                "Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant());

        menuItem.changePrice(new BigDecimal("50.00"));

        assertEquals(new BigDecimal("50.00"), menuItem.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenChangingToInvalidPrice() {
        MenuItem menuItem = MenuItem.create(
                "Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant());

        assertThrows(MenuItemValidationException.class,
                () -> menuItem.changePrice(BigDecimal.ZERO));
    }

    @Test
    void shouldChangeNameCorrectly() {
        MenuItem menuItem = MenuItem.create(
                "Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant());

        menuItem.changeName("Pizza Especial");

        assertEquals("Pizza Especial", menuItem.getName());
    }

    @Test
    void shouldChangeDescriptionCorrectly() {
        MenuItem menuItem = MenuItem.create(
                "Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant());

        menuItem.changeDescription("Nova descrição");

        assertEquals("Nova descrição", menuItem.getDescription());
    }

    @Test
    void shouldChangePhotoPathCorrectly() {
        MenuItem menuItem = MenuItem.create(
                "Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant());

        menuItem.changePhotoPath("/images/nova-foto.jpg");

        assertEquals("/images/nova-foto.jpg", menuItem.getPhotoPath());
    }

    @Test
    void shouldChangeDineInOnlyCorrectly() {
        MenuItem menuItem = MenuItem.create(
                "Pizza", "desc", new BigDecimal("45.90"), false, "/img.jpg", restaurant());

        menuItem.changeDineInOnly(true);

        assertTrue(menuItem.isDineInOnly());
    }

}