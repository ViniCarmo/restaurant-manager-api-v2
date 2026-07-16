package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions;

public class UserAccessDeniedException extends RuntimeException {
    public UserAccessDeniedException(String message) {
        super(message);
    }
}
