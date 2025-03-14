package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.dto.TicketInfoDTO;
import com.demo.flight_booking.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {FlightMapper.class})
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(source = "person.personId", target = "personId")
    @Mapping(source = "flightSeat.flightSeatId", target = "flightSeatId")
    TicketDTO toDTO(Ticket ticket);

    @Mapping(source = "personId", target = "person.personId")
    @Mapping(source = "flightSeatId", target = "flightSeat.flightSeatId")
    Ticket toEntity(TicketDTO ticketDTO);

    @Mapping(source = "ticketId", target = "ticketId")
    @Mapping(source = "ticketPrice", target = "ticketPrice")
    @Mapping(source = "flightSeat.seat.seatNumber", target = "seatNumber")
    @Mapping(source = "flightSeat.seat.seatClass.seatClassName", target = "seatClass")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "flightSeat.flight", target = "flight")
    TicketInfoDTO toTicketInfoDTO(Ticket ticket);
}
