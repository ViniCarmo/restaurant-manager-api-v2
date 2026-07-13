package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.persistence.UserTypeJpaEntity;
import org.springframework.stereotype.Component;

public class UserTypeMapper {

    public static UserTypeJpaEntity toJpaEntity(UserType userType) {
        return new UserTypeJpaEntity(
                userType.getId(),
                userType.getName()
        );
    }

    public static UserType toDomainEntity(UserTypeJpaEntity entity) {
        return new UserType(
                entity.getId(),
                entity.getName()
        );
    }
}