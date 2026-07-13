package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions;

public class UserValidationException extends RuntimeException{
    public UserValidationException(String message) { super(message);
    }
}
