package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception;

public class UserTypeNotFoundException extends RuntimeException{
    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
