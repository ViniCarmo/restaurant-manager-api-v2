package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.response.UserResponseDto;

public class UserPresentationMapper {

    public static UserResponseDto toResponse(User user){

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserType().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
