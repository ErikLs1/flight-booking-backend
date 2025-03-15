package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        BookingResponseDTO response = bookingService.bookFlight(bookingRequest);
        return ResponseEntity.ok(response);
    }
}
