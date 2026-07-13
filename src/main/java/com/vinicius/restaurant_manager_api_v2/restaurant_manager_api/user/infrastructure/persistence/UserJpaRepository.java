package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.persistence;

import aj.org.objectweb.asm.commons.Remapper;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Integer>
{
    Optional<UserJpaEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    void deleteById(UUID id);

    Optional<UserJpaEntity> findById(UUID id);
}
