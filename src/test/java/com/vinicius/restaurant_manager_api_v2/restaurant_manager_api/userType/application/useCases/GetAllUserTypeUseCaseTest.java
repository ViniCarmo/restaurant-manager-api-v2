package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    private GetAllUserTypeUseCase getAllUserTypeUseCase;

    @BeforeEach
    void setUp() {
        getAllUserTypeUseCase = new GetAllUserTypeUseCase(userTypeRepository);
    }

    @Test
    void shouldReturnAllUserTypes() {
        UserType customer = UserType.create("CUSTOMER");
        UserType owner = UserType.create("RESTAURANT OWNER");
        when(userTypeRepository.findAll()).thenReturn(List.of(customer, owner));

        List<UserType> result = getAllUserTypeUseCase.execute();

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoUserTypesExist() {
        when(userTypeRepository.findAll()).thenReturn(List.of());

        List<UserType> result = getAllUserTypeUseCase.execute();

        assertTrue(result.isEmpty());
    }

}