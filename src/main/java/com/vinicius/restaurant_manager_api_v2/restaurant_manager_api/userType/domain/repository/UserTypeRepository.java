package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTypeRepository {
    UserType save(UserType userType);

    Optional<UserType> findById(UUID id);

    Optional<UserType> findByNameIgnoreCase(String name);

    List<UserType> findAll();

    boolean existsByNameIgnoreCase(String name);

    void deleteById(UUID id);

}
