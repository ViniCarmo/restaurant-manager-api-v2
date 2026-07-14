package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception;

public class RestaurantAccessDeniedException extends RuntimeException{
    public RestaurantAccessDeniedException(String message) {
        super(message);
    }
}
