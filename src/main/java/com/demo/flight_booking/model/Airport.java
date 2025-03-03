package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "AIRPORT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    @Column(nullable = false, length = 10, unique = true)
    private String airportCode;

    @Column(nullable = false, length = 100)
    private String airportName;

    @Column(nullable = false, length = 100)
    private String airportCity;

    @Column(nullable = false, length = 100)
    private String airportCountry;

    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL)
    private List<Flight> departureFlights;

    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.ALL)
    private List<Flight> arrivalFlights;
}
