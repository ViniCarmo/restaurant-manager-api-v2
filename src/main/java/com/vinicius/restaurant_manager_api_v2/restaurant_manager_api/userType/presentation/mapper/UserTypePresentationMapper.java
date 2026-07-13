package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.response.UserTypeResponse;

public class UserTypePresentationMapper {
    public static UserTypeResponse toResponse(UserType userType){

        return new UserTypeResponse(
                userType.getId(),
                userType.getName()
        );
    }
}
