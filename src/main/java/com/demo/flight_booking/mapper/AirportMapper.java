package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.model.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportMapper INSTANCE = Mappers.getMapper(AirportMapper.class);

    AirportDTO toDTO(Airport airport);
    Airport toEntity(AirportDTO airportDTO);
}
