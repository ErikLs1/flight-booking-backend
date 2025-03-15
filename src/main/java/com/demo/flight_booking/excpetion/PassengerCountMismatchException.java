package com.demo.flight_booking.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PassengerCountMismatchException extends RuntimeException {
    public PassengerCountMismatchException(String message) {
        super(message);
    }
}
