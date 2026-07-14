package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;


import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.EmailAlreadyInUseException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {


    @Mock
    private UserRepository userRepository;

    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        updateUserUseCase = new UpdateUserUseCase(userRepository);
    }

    @Test
    void shouldUpdateUserWhenDataIsValid() {
        UUID id = UUID.randomUUID();
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmailIgnoreCase("novo@email.com")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = updateUserUseCase.execute(id, "Novo Nome", "novo@email.com");

        assertEquals("Novo Nome", result.getName());
        assertEquals("novo@email.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> updateUserUseCase.execute(id, "Nome", "email@email.com"));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailBelongsToAnotherUser() {
        UUID id = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();
        User user = User.create("Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));
        User otherUser = User.create("Outro", "novo@email.com", "123456", UserType.create("CUSTOMER"));

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmailIgnoreCase("novo@email.com")).thenReturn(Optional.of(otherUser));

        assertThrows(EmailAlreadyInUseException.class,
                () -> updateUserUseCase.execute(id, "Nome", "novo@email.com"));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldAllowUpdateWhenEmailBelongsToSameUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Vinicius", "vinicius@email.com", "123456", UserType.create("CUSTOMER"), LocalDateTime.now(), LocalDateTime.now());
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmailIgnoreCase("vinicius@email.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = updateUserUseCase.execute(id, "Novo Nome", "vinicius@email.com");

        assertEquals("Novo Nome", result.getName());
    }
}