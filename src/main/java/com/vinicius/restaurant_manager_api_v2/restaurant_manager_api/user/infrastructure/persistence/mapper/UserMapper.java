package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.persistence.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.entity.UserJpaEntity;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.persistence.mapper.UserTypeMapper;

public class UserMapper {

    public static UserJpaEntity toJpaEntity(User user) {

        if (user == null) {
            return null;
        }

        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                UserTypeMapper.toJpaEntity(user.getUserType()),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static User toDomainEntity(UserJpaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                UserTypeMapper.toDomainEntity(entity.getUserType()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
