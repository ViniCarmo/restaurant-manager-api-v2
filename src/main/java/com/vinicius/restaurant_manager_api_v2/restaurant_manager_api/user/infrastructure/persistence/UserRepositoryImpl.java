package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository repository;

    public UserRepositoryImpl(UserJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public User save(User user) {
        return UserMapper.toDomainEntity(
                repository.save(UserMapper.toJpaEntity(user))
        );
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id)
                .map(UserMapper::toDomainEntity);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDomainEntity)
                .toList();
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(UserMapper::toDomainEntity);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public void delete(User user) {
        repository.delete(UserMapper.toJpaEntity(user));
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
