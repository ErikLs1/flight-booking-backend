package com.demo.flight_booking.record;

/**
 * Represents the data for an Airport extracted from a JSON file.
 * This record contains basic airport attributes that are later
 * used to create an Airport entity in the database.
 *
 * @param airportCode The airport international code.
 * @param airportName The airport international name.
 * @param airportCity The city in which airport is located.
 * @param airportCountry The country in which the airport is located.
 */
public record AirportRecord(
        String airportCode,
        String airportName,
        String airportCity,
        String airportCountry
) {
}
