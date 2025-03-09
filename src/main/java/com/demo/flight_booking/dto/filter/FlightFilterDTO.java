package com.demo.flight_booking.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for filtering flight search results.
 * This class is user to pass filter criteria.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightFilterDTO {

    private String departureCity;
    private String arrivalCity;
    private String airlineName;
    private Double maxPrice;
    private LocalDateTime departureStartTime;
    private LocalDateTime departureEndTime;
}
