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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserTypeByIdUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    private FindUserTypeByIdUseCase findUserTypeByIdUseCase;

    @BeforeEach
    void setUp() {
        findUserTypeByIdUseCase = new FindUserTypeByIdUseCase(userTypeRepository);
    }

    @Test
    void shouldReturnUserTypeWhenIdExists() {
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create("CUSTOMER");
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));

        UserType result = findUserTypeByIdUseCase.execute(id);

        assertEquals("CUSTOMER", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> findUserTypeByIdUseCase.execute(id));
    }

}