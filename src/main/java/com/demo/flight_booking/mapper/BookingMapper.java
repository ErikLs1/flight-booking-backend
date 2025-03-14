package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "bookingId", target = "bookingId")
    BookingResponseDTO toDTO(Booking booking);
}
