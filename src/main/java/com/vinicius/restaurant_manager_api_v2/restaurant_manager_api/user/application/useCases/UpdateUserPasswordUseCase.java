package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeNotFoundException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;

import java.util.UUID;

@Component
public class UpdateUserPasswordUseCase {

    private final UserRepository userRepository;

    public UpdateUserPasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.updatePassword(newPassword);
        userRepository.save(user);
    }
}
