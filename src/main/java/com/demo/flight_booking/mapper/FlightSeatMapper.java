package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.FlightSeatDTO;
import com.demo.flight_booking.model.FlightSeat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightSeatMapper {
    FlightSeat toEntity(FlightSeatDTO flightSeatDTO);
    FlightSeatDTO toDTO(FlightSeat flightSeat);
}
