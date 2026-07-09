package restaurant.domain.entity;

import restaurant.domain.enums.KitchenType;
import user.domain.entity.User;

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

    private static void validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
    }

    private static void validateAddress(String address){
        if(address == null || address.isBlank()){
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
    }

    public boolean isOpen(LocalTime now){
        return !now.isBefore(openingTime) && !now.isAfter(closingTime);
    }

    public boolean belongsTo(User user) {
        return restaurantOwner.equals(user);

    }

    public void changeOpeningHours(LocalTime openingTime, LocalTime closingTime){
        if (openingTime.isAfter(closingTime)) {
            throw new IllegalArgumentException("Opening time cannot be after closing time.");
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
