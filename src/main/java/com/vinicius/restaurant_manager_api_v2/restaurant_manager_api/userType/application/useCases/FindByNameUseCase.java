package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeNotFoundException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;

@Component
public class FindByNameUseCase {
    private final UserTypeRepository userTypeRepository;

    public FindByNameUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType execute(String name) {
        return userTypeRepository.findByNameIgnoreCase(name).orElseThrow(()
                -> new UserTypeNotFoundException("User type with name " + name + " not found"));
    }
}
