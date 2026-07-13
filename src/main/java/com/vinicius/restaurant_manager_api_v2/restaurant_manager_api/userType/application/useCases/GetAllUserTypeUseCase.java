package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;

import java.util.List;

@Component
public class GetAllUserTypeUseCase {
    private final UserTypeRepository    userTypeRepository;

    public GetAllUserTypeUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> execute() {
        return userTypeRepository.findAll();
    }
}
