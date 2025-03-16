package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.FlightSeatDTO;

import java.util.List;

/**
 * Service interface for managing FlightSeat-related business logic
 *
 * <p>
 *     Extends interface with basic CRUD operations and provides a method to retrieve all flight seats
 *     associated with a given flight.
 * </p>
 */
public interface FlightSeatService extends BasicService<FlightSeatDTO, Long> {
    /**
     * Retrieves all flight seat for a specific flight.
     *
     * @param flightId the id of the flight.
     * @return a list of FlightSeat objects representing the seats on the flight.
     */
    List<FlightSeatDTO> getSeatByFlightId(Long flightId);
}
