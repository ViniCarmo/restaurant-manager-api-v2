package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.UserValidationException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private final UUID id;
    private String name;
    private String email;
    private String password;
    private UserType userType;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(UUID id, String name, String email, String password, UserType userType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new UserValidationException("Name cannot be null or blank");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new UserValidationException("Email cannot be null or blank");
        }
    }


    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new UserValidationException("Password cannot be null or blank");
        }
    }

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeEmail(String newEmail) {
        validateEmail(newEmail);
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        validatePassword(newPassword);
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCustomer() {
        return this.userType.isCustomer();
    }

    public boolean isRestaurantOwner() {
        return this.userType.isRestaurantOwner();
    }

    public static User create(String name, String email, String password, UserType userType) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);
        return new User(UUID.randomUUID(), name, email, password, userType, LocalDateTime.now(), LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
