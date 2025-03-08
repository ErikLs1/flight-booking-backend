package com.demo.flight_booking.record;

public record AircraftRecord(
        String aircraftModel,
        Integer aircraftTotalCapacity,
        Integer aircraftEconomySeats,
        Integer aircraftPremiumSeats,
        Integer aircraftBusinessSeats,
        Integer aircraftFirstClassSeats
        ) {
}
