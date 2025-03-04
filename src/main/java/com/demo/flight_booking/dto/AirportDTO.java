package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {
    private Long airportId;
    private String airportCode;
    private String airportName;
    private String airportCity;
    private String airportCountry;
}
