package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    void delete(User user);

    void deleteById(UUID id);
}
