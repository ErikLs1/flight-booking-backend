package com.demo.flight_booking.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown when a seat with the specified id cannot be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SeatNotFoundException extends RuntimeException {
    /**
     * A new SeatNotFoundException with the specified detailed message.
     *
     * @param message the detail message.
     */
    public SeatNotFoundException(String message) {
        super(message);
    }
}
