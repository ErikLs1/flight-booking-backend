package com.demo.flight_booking.record;

/**
 *  Represents the data for a Seat extracted from a JSON file.
 *  This record contains basic seat attributes that are later
 *  used to create a Seat entity in the database.
 *
 * @param seatNumber The seat number in the plane (e.g., 1A, 2B, 1D).
 * @param rowNumber The seat row number (e.g., 1, 4, 6).
 * @param seatLetter The seat column letter (e.g., A, B, C).
 * @param seatClassName The seat class name (e.g., Economy, Business).
 * @param window The attribute to specify the seat location in the aircraft.
 * @param aisle The attribute to specify the seat location in the aircraft.
 * @param extraLegRoom The attribute to specify the seat location in the aircraft.
 * @param exitRow The attribute to specify the seat location in the aircraft.
 */
public record SeatRecord(
        String seatNumber,
        Integer rowNumber,
        String seatLetter,
        String seatClassName,
        Boolean window,
        Boolean aisle,
        Boolean extraLegRoom,
        Boolean exitRow
) {
}
