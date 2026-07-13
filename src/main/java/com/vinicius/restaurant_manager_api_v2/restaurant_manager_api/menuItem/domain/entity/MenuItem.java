package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.exception.MenuItemNotFoundException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MenuItem {

    private final UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean dineInOnly;
    private String photoPath;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final Restaurant restaurant;

    public MenuItem(UUID id, String name, String description, BigDecimal price, boolean dineInOnly, String photoPath, LocalDateTime createdAt, LocalDateTime updatedAt, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dineInOnly = dineInOnly;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.restaurant = restaurant;
    }

    private static void validateName(String name){
        if (name == null || name.isBlank()) {
            throw new MenuItemNotFoundException("Name cannot be null or empty");
        }
    }

    private static void validatePrice(BigDecimal price){
        if (price == null) {
            throw new MenuItemNotFoundException("Price cannot be null");
        } if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MenuItemNotFoundException("Price must be greater than zero");
        }
    }

    private static void validateRestaurant(Restaurant restaurant){
        if (restaurant == null) {
            throw new MenuItemNotFoundException("Restaurant cannot be null");
        }
    }

    public static MenuItem create(String name, String description, BigDecimal price, boolean dineInOnly, String photoPath, Restaurant restaurant) {
        validateName(name);
        validatePrice(price);
        validateRestaurant(restaurant);
        return new MenuItem(UUID.randomUUID(), name, description, price, dineInOnly, photoPath, LocalDateTime.now(), LocalDateTime.now(), restaurant);
    }

    public void changePrice(BigDecimal price){
        validatePrice(price);
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeName(String name){
        validateName(name);
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeDescription(String description){
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePhotoPath(String photoPath){
        this.photoPath = photoPath;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeDineInOnly(boolean dineInOnly){
        this.dineInOnly = dineInOnly;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDineInOnly() {
        return dineInOnly;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
