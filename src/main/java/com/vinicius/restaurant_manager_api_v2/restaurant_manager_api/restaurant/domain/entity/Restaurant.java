package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantValidationException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Restaurant {

    private final UUID id;
    private String name;
    private String address;
    private KitchenType kitchenType;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private User restaurantOwner;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Restaurant(UUID id, String name, String address, KitchenType kitchenType, LocalTime openingTime, LocalTime closingTime, User restaurantOwner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.kitchenType = kitchenType;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.restaurantOwner = restaurantOwner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Restaurant create(String name, String address, KitchenType kitchenType, LocalTime openingTime, LocalTime closingTime, User restaurantOwner) {
        validateName(name);
        validateAddress(address);
        validateKitchenType(kitchenType);
        validateRestaurantOwner(restaurantOwner);
        validateOpeningHours(openingTime, closingTime);

        return new Restaurant(
                UUID.randomUUID(),
                name,
                address,
                kitchenType,
                openingTime,
                closingTime,
                restaurantOwner,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private static void validateKitchenType(KitchenType kitchenType) {
        if (kitchenType == null) {
            throw new RestaurantValidationException("Kitchen type cannot be null.");
        }
    }

    private static void validateRestaurantOwner(User owner) {
        if (owner == null) {
            throw new RestaurantValidationException("Restaurant owner cannot be null.");
        }
    }

    private static void validateOpeningHours(LocalTime opening, LocalTime closing) {

        if (opening == null || closing == null) {
            throw new RestaurantValidationException("Opening and closing time are required.");
        }

        if (opening.isAfter(closing)) {
            throw new RestaurantValidationException(
                    "Opening time cannot be after closing time.");
        }
    }

    private static void validateName(String name){
        if(name == null || name.isBlank()){
            throw new RestaurantValidationException("Name cannot be null or empty.");
        }
    }



    private static void validateAddress(String address){
        if(address == null || address.isBlank()){
            throw new RestaurantValidationException("Address cannot be null or empty.");
        }
    }

    public boolean isOpen(LocalTime now){
        return !now.isBefore(openingTime) && !now.isAfter(closingTime);
    }

    public boolean belongsTo(User user) {
        return restaurantOwner.getId().equals(user.getId());
    }

    public void changeOpeningHours(LocalTime openingTime, LocalTime closingTime){
        if (openingTime.isAfter(closingTime)) {
            throw new RestaurantValidationException("Opening time cannot be after closing time.");
        }
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeAddress(String address){
        validateAddress(address);
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public KitchenType getKitchenType() {
        return kitchenType;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public User getRestaurantOwner() {
        return restaurantOwner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
