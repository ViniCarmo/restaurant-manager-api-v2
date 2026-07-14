package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    @BeforeEach
    void setUp() {
        updateUserPasswordUseCase = new UpdateUserPasswordUseCase(userRepository);
    }

    @Test
    void shouldUpdatePasswordWhenUserExists() {
        UUID id = UUID.randomUUID();
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        updateUserPasswordUseCase.execute(id, "novaSenha");

        assertEquals("novaSenha", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> updateUserPasswordUseCase.execute(id, "novaSenha"));

        verify(userRepository, never()).save(any());
    }

}