package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents an airport.
 * <p>
 *     Stores information about airport code, name, city and country.
 *     It is linked to Flight entity both as a departure and arrival airport.
 * </p>
 */
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

    /**
     * Unique airport code identifier.
     */
    @Column(nullable = false, length = 10, unique = true)
    private String airportCode;

    /**
     * The full name of the airport.
     */
    @Column(nullable = false, length = 100)
    private String airportName;

    /**
     * The city where the airport is located.
     */
    @Column(nullable = false, length = 100)
    private String airportCity;

    /**
     * The country where the airport is located.
     */
    @Column(nullable = false, length = 100)
    private String airportCountry;

    /**
     * Flights that are departing form this airport.
     */
    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL)
    private List<Flight> departureFlights;

    /**
     * Flight arriving to this airport.
     */
    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.ALL)
    private List<Flight> arrivalFlights;
}
