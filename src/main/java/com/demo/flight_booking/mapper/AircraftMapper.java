package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AircraftDTO;
import com.demo.flight_booking.model.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AircraftMapper {
    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);

    AircraftDTO toDTO(Aircraft aircraft);
    Aircraft toEntity(AircraftDTO aircraftDTO);
}
