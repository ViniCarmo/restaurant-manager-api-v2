package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;

import java.util.UUID;

@Component
public class DeleteUserByIdUseCase {
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public DeleteUserByIdUseCase(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public void execute(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!authenticatedUserProvider.getLoggedUserId().equals(id)) {
            throw new UserAccessDeniedException("You can only delete your own user account");
        }

        userRepository.deleteById(id);
    }
}
