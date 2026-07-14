package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeAlreadyExistsException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeUsecaseTest {


    @Mock
    private UserTypeRepository userTypeRepository;

    private CreateUserTypeUsecase createUserTypeUsecase;

    @BeforeEach
    void setUp() {
        createUserTypeUsecase = new CreateUserTypeUsecase(userTypeRepository);
    }

    @Test
    void shouldCreateUserTypeWhenNameDoesNotExist() {
        when(userTypeRepository.existsByNameIgnoreCase("CUSTOMER")).thenReturn(false);
        when(userTypeRepository.save(any(UserType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserType result = createUserTypeUsecase.execute("CUSTOMER");

        assertNotNull(result);
        assertEquals("CUSTOMER", result.getName());
        verify(userTypeRepository).save(any(UserType.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        when(userTypeRepository.existsByNameIgnoreCase("CUSTOMER")).thenReturn(true);

        assertThrows(UserTypeAlreadyExistsException.class,
                () -> createUserTypeUsecase.execute("CUSTOMER"));

        verify(userTypeRepository, never()).save(any());
    }
}
