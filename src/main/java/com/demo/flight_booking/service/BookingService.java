package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.booking.BookingDTO;
import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
/**
 * Service interface for managing booking-related business logic.
 * <p>
 *     Provide basic methods for CRUD operations and a method for processing a complete booking request
 *     that includes multiple passengers, seat selections, payment method and the flight id.
 * </p>
 */
public interface BookingService extends BasicService<BookingDTO, Long> {
    /**
     * Process a flight booking request.
     *
     * <p>
     *     The method validates the booking request, ensuring that the flight exists, the number of
     *     passengers matches the number of seat ids, and that all required seats are available.
     *     It then creates a booking with tickets  and a payments.
     * </p>
     * @param bookingRequest
     * @return
     */
    BookingResponseDTO bookFlight(BookingRequestDTO bookingRequest);
}
