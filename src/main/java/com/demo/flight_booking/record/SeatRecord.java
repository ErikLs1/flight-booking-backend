package com.demo.flight_booking.record;


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
