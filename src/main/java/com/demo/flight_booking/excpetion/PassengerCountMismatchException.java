package com.demo.flight_booking.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepting is thrown when the number of passengers provided does not match the number of seat ids.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PassengerCountMismatchException extends RuntimeException {
    /**
     * A new PassengerCountMismatchException with the specified detailed message.
     *
     * @param message the detail message.
     */
    public PassengerCountMismatchException(String message) {
        super(message);
    }
}
