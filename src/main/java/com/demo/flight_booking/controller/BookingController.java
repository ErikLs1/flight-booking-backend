package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling flight booking.
 * <p>
 *     Provides and endpoint to create a new booking.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingServiceImpl bookingService;

    /**
     * Create a new booking based on the provided booking request
     *
     * @param bookingRequest the booking request containing flight id, passenger details,
     *                       seat ids, and payment method.
     * @return a ResponseEntity containing a BookingResponseDTO with the created booking id.
     */
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        BookingResponseDTO response = bookingService.bookFlight(bookingRequest);
        return ResponseEntity.ok(response);
    }
}
