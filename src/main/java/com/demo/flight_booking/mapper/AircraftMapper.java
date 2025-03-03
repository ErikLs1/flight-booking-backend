package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AircraftDTO;
import com.demo.flight_booking.model.Aircraft;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AircraftMapper {
    Aircraft toEntity(AircraftDTO aircraftDTO);
    AircraftDTO toDTO(Aircraft aircraft);
}
