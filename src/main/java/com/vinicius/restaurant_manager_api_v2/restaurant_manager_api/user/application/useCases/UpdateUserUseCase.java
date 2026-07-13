package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.EmailAlreadyInUseException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id, String name, String email) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Optional<User> existing = userRepository.findByEmailIgnoreCase(email);

        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new EmailAlreadyInUseException(email);
        }

        user.changeEmail(email);

        return userRepository.save(user);
    }
}
