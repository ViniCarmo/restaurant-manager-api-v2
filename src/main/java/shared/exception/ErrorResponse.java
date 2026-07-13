package shared.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, Integer status, String message){
    public static ErrorResponse of(Integer status, String message) {
        return new ErrorResponse(LocalDateTime.now(), status, message);
    }
}
