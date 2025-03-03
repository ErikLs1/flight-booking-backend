package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.model.Airline;
import com.demo.flight_booking.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    Airline toEntity(AirportDTO airportDTO);
    AirportDTO toDTO(Airline airline);
}
