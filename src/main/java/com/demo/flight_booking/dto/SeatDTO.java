package com.demo.flight_booking.dto;

import com.demo.flight_booking.model.Aircraft;
import com.demo.flight_booking.model.SeatClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long seatId;
    private SeatClass seatClassId;
    private Aircraft aircraftId;
    private String seatNumber;
}
