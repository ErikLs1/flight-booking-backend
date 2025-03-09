package com.demo.flight_booking.record;

public record AirportRecord(
        String airportCode,
        String airportName,
        String airportCity,
        String airportCountry
) {
}
