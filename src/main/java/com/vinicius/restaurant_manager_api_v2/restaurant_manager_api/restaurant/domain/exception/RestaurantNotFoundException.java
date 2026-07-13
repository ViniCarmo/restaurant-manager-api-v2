package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message){super(message);
    }
}
