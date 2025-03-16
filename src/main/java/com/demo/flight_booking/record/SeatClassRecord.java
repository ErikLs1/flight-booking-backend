package com.demo.flight_booking.record;

/**
 *  Represents the data for a Seat Class extracted from a JSON file.
 *  This record contains basic Seat Class attributes that are later
 *  used to create a SeatClass entity in the database.
 *
 * @param seatClassName The seat class name (e.g., Business, Economy, First Class).
 * @param basePrice The additional fee for the specific class.
 */
public record SeatClassRecord(
        String seatClassName,
        Double basePrice
) {
}
