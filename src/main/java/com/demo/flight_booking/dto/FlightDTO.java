package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long flightId;
    private Long airlineId;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private Long aircraftId;

    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double basePrice;
}
