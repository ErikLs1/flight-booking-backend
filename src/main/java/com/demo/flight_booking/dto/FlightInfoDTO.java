package com.demo.flight_booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
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
