package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.model.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight toEntity(FlightDTO flightDTO);
    FlightDTO toDTO(Flight flight);
}
