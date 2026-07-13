package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeAlreadyExistsException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;

@Component
public class CreateUserTypeUsecase {
    private final UserTypeRepository userTypeRepository;

    public CreateUserTypeUsecase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType Execute(String name) {
        if (userTypeRepository.existsByNameIgnoreCase(name)) {
            throw new UserTypeAlreadyExistsException("UserType with name " + name + " already exists");
        }

        UserType userType = UserType.create(name);

        return userTypeRepository.save(userType);
    }
}
