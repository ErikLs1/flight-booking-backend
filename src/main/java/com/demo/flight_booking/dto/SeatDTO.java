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
    private Integer rowNumber;
    private String seatLetter;
    private Boolean extraLegRoom;
    private Boolean nearExit;
    private Boolean window;
    private Boolean aisle;
}
