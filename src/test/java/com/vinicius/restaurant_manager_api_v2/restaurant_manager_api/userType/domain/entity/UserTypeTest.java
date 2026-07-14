package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {

    @Test
    void shouldCreateUserTypeWithUppercaseName() {
        UserType userType = UserType.create("customer");

        assertEquals("CUSTOMER", userType.getName());
        assertNotNull(userType.getId());
    }

    @Test
    void shouldTrimNameWhenCreating() {
        UserType userType = UserType.create("  customer  ");

        assertEquals("CUSTOMER", userType.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(UserTypeValidationException.class,
                () -> UserType.create(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(UserTypeValidationException.class,
                () -> UserType.create("   "));
    }

    @Test
    void shouldReturnTrueWhenIsCustomer() {
        UserType userType = UserType.create("customer");

        assertTrue(userType.isCustomer());
        assertFalse(userType.isRestaurantOwner());
    }

    @Test
    void shouldReturnTrueWhenIsRestaurantOwner() {
        UserType userType = UserType.create("restaurant owner");

        assertTrue(userType.isRestaurantOwner());
        assertFalse(userType.isCustomer());
    }

    @Test
    void shouldChangeNameCorrectly() {
        UserType userType = UserType.create("customer");

        userType.changeName("restaurant owner");

        assertEquals("RESTAURANT OWNER", userType.getName());
        assertTrue(userType.isRestaurantOwner());
    }

    @Test
    void shouldThrowExceptionWhenChangingToBlankName() {
        UserType userType = UserType.create("customer");

        assertThrows(UserTypeValidationException.class,
                () -> userType.changeName(""));
    }


}