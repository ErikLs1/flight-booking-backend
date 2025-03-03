package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketDTO ticketDTO);
    TicketDTO toDTO(Ticket ticket);
}
