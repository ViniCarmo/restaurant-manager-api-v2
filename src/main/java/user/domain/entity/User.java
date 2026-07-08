package user.domain.entity;

import userType.domain.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private final UUID id;
    private String name;
    private String email;
    private String password;
    private UserType userType;
    private LocalDateTime createdAt;
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

    public void changeName(String newName){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeEmail(String newEmail){
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCustomer(){
        return this.userType.isCustomer();
    }

    public boolean isRestaurantOwner(){
        return this.userType.isRestaurantOwner();
    }

    public static User create(String name, String email, String password, UserType userType) {
        return new User(null, name, email, password, userType, LocalDateTime.now(), LocalDateTime.now());
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
