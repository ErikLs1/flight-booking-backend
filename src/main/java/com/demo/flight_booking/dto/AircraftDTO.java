package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDTO {
    private Long aircraftId;
    private String aircraftModel;
    private Integer aircraftTotalCapacity;
    private Integer aircraftEconomySeats;
    private Integer aircraftPremiumSeats;
    private Integer aircraftBusinessSeats;
    private Integer aircraftFirstClassSeats;
}
