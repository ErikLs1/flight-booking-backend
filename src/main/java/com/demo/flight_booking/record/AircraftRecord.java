package com.demo.flight_booking.record;

/**
 * Represents the data for the Aircraft extracted from a JSON file.
 * This record contains basic aircraft attributes that are later
 * used to create an Aircraft entity in the database.
 *
 * @param aircraftModel The aircraft model name.
 * @param aircraftTotalCapacity The total number of seats in the aircraft.
 * @param aircraftEconomySeats The number of Economy seats in the aircraft.
 * @param aircraftPremiumSeats The number of Premium seat in the aircraft.
 * @param aircraftBusinessSeats The number of Business seats in the aircraft.
 * @param aircraftFirstClassSeats The number of First Class seats in the aircraft.
 */
public record AircraftRecord(
        String aircraftModel,
        Integer aircraftTotalCapacity,
        Integer aircraftEconomySeats,
        Integer aircraftPremiumSeats,
        Integer aircraftBusinessSeats,
        Integer aircraftFirstClassSeats
        ) {
}
