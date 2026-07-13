package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception;

public class RestaurantOwnerRequiredException extends RuntimeException{
    public RestaurantOwnerRequiredException(String message) {
        super(message);
    }
}
