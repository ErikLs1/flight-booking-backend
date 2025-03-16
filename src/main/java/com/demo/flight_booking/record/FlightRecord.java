package com.demo.flight_booking.record;

/**
 *  Represents the data for a Flight extracted from a JSON file.
 *  This record contains basic flight attributes that are later
 *  used to create a Flight entity in the database.
 *
 * @param flightNumber The flight number.
 * @param airlineIATACode The airline IATA code which will perform the flight.
 * @param departureAirportCode The departure airport international code.
 * @param arrivalAirportCode The arrival airport international code.
 * @param aircraftModel The aircraft model that will be used for the flight.
 * @param basePrice The price for the flight (ticket price not included).
 * @param departureTime The departure time of the flight.
 * @param arrivalTime The arrival time of the flight.
 */
public record FlightRecord(
        String flightNumber,
        String airlineIATACode,
        String departureAirportCode,
        String arrivalAirportCode,
        String aircraftModel,
        Double basePrice,
        String departureTime,
        String arrivalTime
) {
}
