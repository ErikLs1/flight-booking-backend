package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.booking.BookingDTO;
import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;

public interface BookingService extends BasicService<BookingDTO, Long> {
    BookingResponseDTO bookFlight(BookingRequestDTO bookingRequest);
}
