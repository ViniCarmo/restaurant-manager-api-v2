package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.exception.UserTypeValidationException;

import java.util.UUID;

public class UserType {
    private final UUID id;
    private String name;

    public UserType(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isCustomer() {
        return "CUSTOMER".equals(name);
    }

    public boolean isRestaurantOwner(){
        return "RESTAURANT OWNER".equalsIgnoreCase(name);
    }

    public static UserType create(String name) {
        validateName(name);
        return new UserType(UUID.randomUUID(), name.trim().toUpperCase());
    }

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName.trim().toUpperCase();
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new UserTypeValidationException("User type name cannot be null or blank.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
