package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(source = "airline.airlineId", target = "airlineId")
    @Mapping(source = "departureAirport.airportId", target = "departureAirportId")
    @Mapping(source = "arrivalAirport.airportId", target = "arrivalAirportId")
    @Mapping(source = "aircraft.aircraftId", target = "aircraftId")
    FlightDTO toDTO(Flight flight);

    @Mapping(source = "airlineId", target = "airline.airlineId")
    @Mapping(source = "departureAirportId", target = "departureAirport.airportId")
    @Mapping(source = "arrivalAirportId", target = "arrivalAirport.airportId")
    @Mapping(source = "aircraftId", target = "aircraft.aircraftId")
    Flight toEntity(FlightDTO flightDTO);
}
