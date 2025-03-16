package com.demo.flight_booking.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a flight with the specified id was not found in the database.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FlightNoFoundException extends RuntimeException {
    /**
     * A new FlightNotFoundException with the specified detailed message.
     *
     * @param message the detailed message.
     */
    public FlightNoFoundException(String message) {
        super(message);
    }
}
