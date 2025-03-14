package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private String airlineName;
    private String departureCity;
    private String arrivalCity;
    private List<SeatClassFeeDto> seatClasses;
}
