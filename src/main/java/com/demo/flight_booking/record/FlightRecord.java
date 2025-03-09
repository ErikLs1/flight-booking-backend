package com.demo.flight_booking.record;

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
