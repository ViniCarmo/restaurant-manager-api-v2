package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception;

public class MenuItemValidationException extends RuntimeException {
    public MenuItemValidationException(String message) {
        super(message);
    }
}
