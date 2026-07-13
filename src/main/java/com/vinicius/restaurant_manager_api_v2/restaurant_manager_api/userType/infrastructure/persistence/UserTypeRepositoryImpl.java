package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.persistence;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.persistence.mapper.UserTypeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserTypeRepositoryImpl implements UserTypeRepository {

    private final UserTypeJpaRepository repository;

    public UserTypeRepositoryImpl(UserTypeJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserType save(UserType userType) {
        return UserTypeMapper.toDomainEntity(
                repository.save(
                        UserTypeMapper.toJpaEntity(userType)
                )
        );
    }

    @Override
    public Optional<UserType> findById(UUID id) {
        return repository.findById(id)
                .map(UserTypeMapper::toDomainEntity);
    }

    @Override
    public List<UserType> findAll() {
        return repository.findAll()
                .stream()
                .map(UserTypeMapper::toDomainEntity)
                .toList();
    }

    @Override
    public Optional<UserType> findByNameIgnoreCase(String name) {
        return repository.findByNameIgnoreCase(name)
                .map(UserTypeMapper::toDomainEntity);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    @Override
    public void delete(UserType userType) {
        repository.delete(
                UserTypeMapper.toJpaEntity(userType)
        );
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
