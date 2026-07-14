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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByNameUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    private FindByNameUseCase findByNameUseCase;

    @BeforeEach
    void setUp() {
        findByNameUseCase = new FindByNameUseCase(userTypeRepository);
    }

    @Test
    void shouldReturnUserTypeWhenNameExists() {
        UserType userType = UserType.create("CUSTOMER");
        when(userTypeRepository.findByNameIgnoreCase("CUSTOMER")).thenReturn(Optional.of(userType));

        UserType result = findByNameUseCase.execute("CUSTOMER");

        assertEquals("CUSTOMER", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameDoesNotExist() {
        when(userTypeRepository.findByNameIgnoreCase("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> findByNameUseCase.execute("UNKNOWN"));
    }

}