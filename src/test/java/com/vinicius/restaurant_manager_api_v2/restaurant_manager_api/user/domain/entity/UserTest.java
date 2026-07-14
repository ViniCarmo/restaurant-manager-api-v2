package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserValidationException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private UserType customerType() {
        return UserType.create("customer");
    }

    private UserType restaurantOwnerType() {
        return UserType.create("restaurant owner");
    }

    @Test
    void shouldCreateUserWithValidData() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());

        assertNotNull(user.getId());
        assertEquals("Vinicius", user.getName());
        assertEquals("vinicius@email.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(UserValidationException.class,
                () -> User.create(null, "vinicius@email.com", "123456", customerType()));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(UserValidationException.class,
                () -> User.create("   ", "vinicius@email.com", "123456", customerType()));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(UserValidationException.class,
                () -> User.create("Vinicius", null, "123456", customerType()));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank() {
        assertThrows(UserValidationException.class,
                () -> User.create("Vinicius", "  ", "123456", customerType()));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThrows(UserValidationException.class,
                () -> User.create("Vinicius", "vinicius@email.com", null, customerType()));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsBlank() {
        assertThrows(UserValidationException.class,
                () -> User.create("Vinicius", "vinicius@email.com", "  ", customerType()));
    }

    @Test
    void shouldReturnTrueWhenUserIsCustomer() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());

        assertTrue(user.isCustomer());
        assertFalse(user.isRestaurantOwner());
    }

    @Test
    void shouldReturnTrueWhenUserIsRestaurantOwner() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", restaurantOwnerType());

        assertTrue(user.isRestaurantOwner());
        assertFalse(user.isCustomer());
    }

    @Test
    void shouldChangeNameCorrectly() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());

        user.changeName("Carmo");

        assertEquals("Carmo", user.getName());
    }

    @Test
    void shouldThrowExceptionWhenChangingToBlankName() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());

        assertThrows(UserValidationException.class,
                () -> user.changeName(""));
    }

    @Test
    void shouldChangeEmailCorrectly() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());

        user.changeEmail("novo@email.com");

        assertEquals("novo@email.com", user.getEmail());
    }

    @Test
    void shouldChangePasswordCorrectly() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());

        user.updatePassword("novaSenha");

        assertEquals("novaSenha", user.getPassword());
    }

    @Test
    void shouldUpdateTimestampWhenChangingName() throws InterruptedException {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", customerType());
        var originalUpdatedAt = user.getUpdatedAt();

        Thread.sleep(10);
        user.changeName("Carmo");

        assertTrue(user.getUpdatedAt().isAfter(originalUpdatedAt));
    }

}