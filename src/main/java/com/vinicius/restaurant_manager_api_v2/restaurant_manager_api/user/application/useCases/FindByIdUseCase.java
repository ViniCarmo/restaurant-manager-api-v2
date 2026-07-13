package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;

import java.util.UUID;

@Component
public class FindByIdUseCase {

    private final UserRepository userRepository;


    public FindByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
