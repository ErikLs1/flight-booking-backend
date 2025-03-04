package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.model.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    AirlineMapper INSTANCE = Mappers.getMapper(AirlineMapper.class);

    AirportDTO toDTO(Airline airline);
    Airline toEntity(AirportDTO airportDTO);
}
