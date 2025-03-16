package com.demo.flight_booking.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the seat that user wanted to book is already marked as booked.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SeatAlreadyBookedException extends RuntimeException {
    /**
     * A new SeatAlreadyBookedException with the specified detailed message.
     * @param message the detail message.
     */
    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}
