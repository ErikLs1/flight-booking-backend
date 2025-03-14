package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.FlightInfoDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.model.SeatClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(source = "airline.airlineId", target = "airlineId")
    @Mapping(source = "airline.airlineName", target = "airlineName")
    @Mapping(source = "departureAirport.airportId", target = "departureAirportId")
    @Mapping(source = "departureAirport.airportCity", target = "departureCity")
    @Mapping(source = "arrivalAirport.airportId", target = "arrivalAirportId")
    @Mapping(source = "arrivalAirport.airportCity", target = "arrivalCity")
    @Mapping(source = "aircraft.aircraftId", target = "aircraftId")
    @Mapping(source = "aircraft.seatClasses", target = "seatClasses")
    FlightDTO toDTO(Flight flight);


    @Mapping(source = "airlineId", target = "airline.airlineId")
    @Mapping(source = "departureAirportId", target = "departureAirport.airportId")
    @Mapping(source = "arrivalAirportId", target = "arrivalAirport.airportId")
    @Mapping(source = "aircraftId", target = "aircraft.aircraftId")
    Flight toEntity(FlightDTO flightDTO);


    @Mapping(source = "flightId", target = "flightId")
    @Mapping(source = "flightNumber", target = "flightNumber")
    @Mapping(source = "departureAirport.airportCity", target = "departureCity")
    @Mapping(source = "departureAirport.airportCountry", target = "departureCountry")
    @Mapping(source = "arrivalAirport.airportCity", target = "arrivalCity")
    @Mapping(source = "arrivalAirport.airportCountry", target = "arrivalCountry")
    @Mapping(source = "departureTime", target = "departureTime")
    @Mapping(source = "arrivalTime", target = "arrivalTime")
    FlightInfoDTO toFlightInfoDTO(Flight flight);

    @Mapping(source = "basePrice", target = "baseFee")
    SeatClassFeeDto toSeatClassFeeDto(SeatClass seatClass);
}
