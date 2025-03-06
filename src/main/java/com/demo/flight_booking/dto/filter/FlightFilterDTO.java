package com.demo.flight_booking.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for filtering flight search results.
 * This class is user to pass filter criteria.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightFilterDTO {
    /**
     * The id of departure airport.
     */
    private Long departureAirportId;

    /**
     * The id of arrival airport.
     */
    private Long arrivalAirportId;
    /**
     * The id of the airline.
     */
    private Long airlineId;

    /**
     * The maximum price for the flight.
     */
    private Double maxPrice;

    /**
     * The departure start time for the flight.
     */
    private LocalDateTime departureStartTime;

    /**
     * The departure end time for the flight.
     */
    private LocalDateTime departureEndTime;
}
