package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, Integer status, String message){
    public static ErrorResponse of(Integer status, String message) {
        return new ErrorResponse(LocalDateTime.now(), status, message);
    }
}
