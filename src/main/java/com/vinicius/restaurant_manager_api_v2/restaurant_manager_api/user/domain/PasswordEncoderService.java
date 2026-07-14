package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain;

public interface PasswordEncoderService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
