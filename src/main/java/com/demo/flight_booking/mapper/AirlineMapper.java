package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AirlineDTO;
import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.model.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    AirlineMapper INSTANCE = Mappers.getMapper(AirlineMapper.class);

    AirlineDTO toDTO(Airline airline);
    Airline toEntity(AirlineDTO airlineDTO);
}
