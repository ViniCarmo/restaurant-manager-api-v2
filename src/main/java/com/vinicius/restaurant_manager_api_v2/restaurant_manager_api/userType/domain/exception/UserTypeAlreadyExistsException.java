package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception;

public class UserTypeAlreadyExistsException extends RuntimeException{
    public UserTypeAlreadyExistsException(String message) {super(message);}
}
