package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.dto.TicketInfoDTO;

import java.util.List;

/**
 *  Service interface for managing ticket-related business logic.
 *
 *  <p>
 *      Extends interface for basic CRUD operations and provides additional method to retrieve ticket
 *      information by using a passengers email.
 *  </p>
 */
public interface TicketService extends BasicService<TicketDTO, Long> {
    /**
     * Retrieves detailed ticket information associated with the specific email.
     *
     * @param email the email address of the passenger.
     * @return the list of TicketInfoDTO objects containing ticket, passenger, and flight information.
     */
    List<TicketInfoDTO> getTicketsByEmail(String email);
}
