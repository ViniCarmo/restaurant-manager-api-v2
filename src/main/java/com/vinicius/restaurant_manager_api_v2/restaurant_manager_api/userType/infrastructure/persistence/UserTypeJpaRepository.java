package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.entity.UserTypeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserTypeJpaRepository extends JpaRepository<UserTypeJpaEntity, UUID> {
    Optional<UserTypeJpaEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
