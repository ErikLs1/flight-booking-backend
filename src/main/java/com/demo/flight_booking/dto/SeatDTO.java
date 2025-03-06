package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long seatId;
    private Long seatClassId;
    private Long aircraftId;
    private String seatNumber;
}
