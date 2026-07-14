package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.PasswordEncoderService;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.EmailAlreadyInUseException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;
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
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUserUseCase(userRepository, userTypeRepository, passwordEncoderService);
    }

    @Test
    void shouldCreateUserWhenEmailAndUserTypeAreValid() {
        UUID userTypeId = UUID.randomUUID();
        UserType userType = UserType.create("CUSTOMER");

        when(userRepository.findByEmailIgnoreCase("vinicius@email.com")).thenReturn(Optional.empty());
        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.of(userType));
        when(passwordEncoderService.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = createUserUseCase.execute("Vinicius", "vinicius@email.com", "123456", userTypeId);

        assertNotNull(result);
        assertEquals("Vinicius", result.getName());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyInUse() {
        UUID userTypeId = UUID.randomUUID();
        User existingUser = User.create("Outro", "vinicius@email.com", "123456", UserType.create("CUSTOMER"));

        when(userRepository.findByEmailIgnoreCase("vinicius@email.com")).thenReturn(Optional.of(existingUser));

        assertThrows(EmailAlreadyInUseException.class,
                () -> createUserUseCase.execute("Vinicius", "vinicius@email.com", "123456", userTypeId));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        UUID userTypeId = UUID.randomUUID();

        when(userRepository.findByEmailIgnoreCase("vinicius@email.com")).thenReturn(Optional.empty());
        when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> createUserUseCase.execute("Vinicius", "vinicius@email.com", "123456", userTypeId));

        verify(userRepository, never()).save(any());
    }

}