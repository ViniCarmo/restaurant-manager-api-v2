package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeByIdUsecaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    private DeleteUserTypeByIdUsecase deleteUserTypeByIdUsecase;

    @BeforeEach
    void setUp() {
        deleteUserTypeByIdUsecase = new DeleteUserTypeByIdUsecase(userTypeRepository);
    }

    @Test
    void shouldDeleteUserTypeWhenItExists() {
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create("CUSTOMER");
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));

        deleteUserTypeByIdUsecase.execute(id);

        verify(userTypeRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> deleteUserTypeByIdUsecase.execute(id));

        verify(userTypeRepository, never()).deleteById(any());
    }

}