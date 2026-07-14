package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.security;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.PasswordEncoderService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderService implements PasswordEncoderService {
    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordEncoderService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
