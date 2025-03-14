package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.dto.TicketInfoDTO;

import java.util.List;

public interface TicketService extends BasicService<TicketDTO, Long> {
    List<TicketInfoDTO> getTicketsByEmail(String email);
}
