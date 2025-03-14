package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfoDTO {
    private Long flightId;
    private String flightNumber;
    private String departureCity;
    private String departureCountry;
    private String arrivalCity;
    private String arrivalCountry;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}
