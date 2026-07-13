package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeNotFoundException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;

import java.util.UUID;

@Component
public class FindUserTypeByIdUseCase {

    private final UserTypeRepository userTypeRepository;

    public FindUserTypeByIdUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType execute(UUID id) {
        return userTypeRepository.findById(id).orElseThrow(()
                -> new UserTypeNotFoundException("User type with ID " + id + " not found"));
    }
}
