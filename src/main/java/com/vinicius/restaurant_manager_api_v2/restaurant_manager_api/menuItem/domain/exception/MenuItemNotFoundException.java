package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
