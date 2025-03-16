package com.demo.flight_booking.record;

/**
 * Represents the data for an Airline extracted from a JSON file.
 * This record contains basic airline attributes that are later
 * used to create an Airline entity in the database.
 *
 * @param airlineName The airline name.
 * @param airlineIATACode The airline IATA code.
 */
public record AirlineRecord(
        String airlineName,
        String airlineIATACode
) {
}
