package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String email){
        super("Email " + email + " is already in use.");
    }
}
