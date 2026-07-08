package userType.domain;

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
        return "RESTAURANT_OWNER".equals(name);
    }

    public static UserType create(String name) {
        return new UserType(UUID.randomUUID(), name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
