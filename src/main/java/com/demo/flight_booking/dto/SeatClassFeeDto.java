package com.demo.flight_booking.dto;

import com.demo.flight_booking.model.enums.SeatClassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatClassFeeDto {
    private SeatClassType seatClassName;
    private Double baseFee;
}
