package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.PasswordEncoderService;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.EmailAlreadyInUseException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;

import java.util.UUID;


@Component
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoderService passwordEncoderService;

    public CreateUserUseCase(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoderService passwordEncoderService) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    public User execute(String name, String email, String password, UUID userTypeId) {
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new EmailAlreadyInUseException(email);
        }
        UserType userType = userTypeRepository.findById(userTypeId)
                .orElseThrow(() ->
                        new UserTypeNotFoundException("UserType not found"));

        String encodedPassword = passwordEncoderService.encode(password);

        User user = User.create(
                name,
                email,
                encodedPassword,
                userType
        );

        return userRepository.save(user);
    }
    }

