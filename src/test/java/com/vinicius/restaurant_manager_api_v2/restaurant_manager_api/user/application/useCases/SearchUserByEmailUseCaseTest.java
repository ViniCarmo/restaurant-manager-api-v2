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
class SearchUserByEmailUseCaseTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private SearchUserByEmailUseCase searchUserByEmailUseCase;

    @BeforeEach
    void setUp() {
        searchUserByEmailUseCase = new SearchUserByEmailUseCase(userRepository, authenticatedUserProvider);
    }

    @Test
    void shouldReturnUserWhenEmailExistsAndBelongsToLoggedUser() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        when(userRepository.findByEmailIgnoreCase("vinicius@email.com")).thenReturn(Optional.of(user));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(user.getId());

        User result = searchUserByEmailUseCase.execute("vinicius@email.com");

        assertEquals("vinicius@email.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {
        when(userRepository.findByEmailIgnoreCase("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> searchUserByEmailUseCase.execute("naoexiste@email.com"));
    }

    @Test
    void shouldThrowExceptionWhenEmailBelongsToAnotherUser() {
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        when(userRepository.findByEmailIgnoreCase("vinicius@email.com")).thenReturn(Optional.of(user));
        when(authenticatedUserProvider.getLoggedUserId()).thenReturn(UUID.randomUUID());

        assertThrows(UserAccessDeniedException.class,
                () -> searchUserByEmailUseCase.execute("vinicius@email.com"));
    }

}