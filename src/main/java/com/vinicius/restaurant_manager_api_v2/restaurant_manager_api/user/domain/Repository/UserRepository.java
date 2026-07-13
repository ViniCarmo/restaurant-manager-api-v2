package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    void deleteById(UUID id);
}
