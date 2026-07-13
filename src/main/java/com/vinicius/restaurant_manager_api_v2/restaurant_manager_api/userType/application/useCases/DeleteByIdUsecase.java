package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeNotFoundException;
import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;

import java.util.UUID;

@Component
public class DeleteByIdUsecase {
    private final UserTypeRepository userTypeRepository;

    public DeleteByIdUsecase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public void execute(UUID id){
        userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException("UserType not found"));
        userTypeRepository.deleteById(id);
    }
}
