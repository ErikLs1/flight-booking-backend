package com.demo.flight_booking.dto;

import com.demo.flight_booking.model.enums.SeatClassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatClassDTO {
    private Long seatClassId;
    private SeatClassType seatClassName;
    private Boolean extraLegRoom;
    private Boolean nearExit;
    private Boolean window;
    private Boolean aisle;
    private Double basePrice;
}
