package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.model.enums.SeatClassType;

import java.util.List;

/**
 * Service interface for managing flight-related business logic.
 *
 * <p>
 *     Extends the interface with for basic CRUD operations and provides additional
 *     methods for searching flights and retrieving flight specific data.
 * </p>
 */
public interface FlightService extends BasicService<FlightDTO, Long> {

    /**
     * Searches flights based on the provided filters.
     *
     * @param filter a FlightFilterDTO containing search parameters.
     * @return a list of FlightDTO objects that match the filtering criteria.
     */
    List<FlightDTO> searchFlights(FlightFilterDTO filter);

    /**
     * Retrieves the aircraft model associated with a specific flight.
     *
     * @param flightId the id of the flight.
     * @return a String representing the aircraft model.
     */
    String getAircraftModelByFlightId(Long flightId);

    /**
     * Retrieves the seat class types available on a specific flight.
     *
     * @param flightId the id of the flight.
     * @return a list of enums representing the available seat classes.
     */
    List<SeatClassType> getSeatClassesByFlightId(Long flightId);

    /**
     * Retrieves the departure and arrival cities for a specific flight.
     *
     * @param flightId the id of the flight.
     * @return a list of String values where first element is the departure city and the second is the arrival city.
     */
    List<String> getFlightCitiesByFlightId(Long flightId);

    /**
     * Retrieves fee information for each seat class on a specific flight.
     *
     * @param flightId the id of the flight.
     * @return a list of SeatClassFeeDTO objects containing seat class name and their associated fees.
     */
    List<SeatClassFeeDto> getSeatClassesForFlight(Long flightId);
}
