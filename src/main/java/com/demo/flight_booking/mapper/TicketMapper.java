package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(source = "person.personId", target = "personId")
    @Mapping(source = "flightSeat.flightSeatId", target = "flightSeatId")
    TicketDTO toDTO(Ticket ticket);

    @Mapping(source = "personId", target = "person.personId")
    @Mapping(source = "flightSeatId", target = "flightSeat.flightSeatId")
    Ticket toEntity(TicketDTO ticketDTO);
}
