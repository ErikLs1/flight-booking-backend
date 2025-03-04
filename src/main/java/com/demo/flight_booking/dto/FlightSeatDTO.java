package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSeatDTO {
    private Long flightSeatId;
    private Long seatId;
    private Long flightId;
    private Boolean isBooked;
}
