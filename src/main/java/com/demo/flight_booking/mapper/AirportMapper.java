package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.model.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    Airport toEntity(AirportDTO airportDTO);
    AirportDTO toDTO(Airport airport);
}
