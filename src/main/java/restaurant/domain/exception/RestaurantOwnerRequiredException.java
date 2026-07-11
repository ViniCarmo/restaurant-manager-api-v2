package restaurant.domain.exception;

public class RestaurantOwnerRequiredException extends RuntimeException{
    public RestaurantOwnerRequiredException(String message) {
        super(message);
    }
}
