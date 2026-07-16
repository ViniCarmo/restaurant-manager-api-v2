package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.AuthenticatedUserProvider;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserAccessDeniedException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private FindUserByIdUseCase findUserByIdUseCase;

    @BeforeEach
    void setUp() {
        findUserByIdUseCase = new FindUserByIdUseCase(userRepository, authenticatedUserProvider);
    }

    @Test
    void shouldReturnUserWhenIdExistsAndBelongsToLoggedUser() {
        UUID id = UUID.randomUUID();
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(id);

        User result = findUserByIdUseCase.execute(id);

        assertEquals("Vinicius", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> findUserByIdUseCase.execute(id));
    }

    @Test
    void shouldThrowExceptionWhenLoggedUserIsNotTheTargetUser() {
        UUID id = UUID.randomUUID();
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(UUID.randomUUID());

        assertThrows(UserAccessDeniedException.class,
                () -> findUserByIdUseCase.execute(id));
    }

}