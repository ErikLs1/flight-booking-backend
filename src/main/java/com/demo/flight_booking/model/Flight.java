package com.demo.flight_booking.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents flight.
 *
 * <p>
 *     Stores information about a specific flight, like flight number, departure and arrival time and
 *     pricing information.
 *     It is linked with departure and arrival airports, Aircraft and the FlightSeats for the flight.
 * </p>
 */
@Entity
@Table(name = "FLIGHT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    /**
     * The airline operating the flight.
     */
    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    /**
     * The airport from which this flight departs.
     */
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    /**
     * The airport where this flight arrives.
     */
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    /**
     * The aircraft used for the flights.
     */
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    /**
     * The flight number.
     */
    @Column(nullable = false, length = 20)
    private String flightNumber;

    /**
     * The departure time of the flight.
     */
    @Column(nullable = false)
    private LocalDateTime departureTime;

    /**
     * The arrival time of the flight.
     */
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    /**
     * The base price for the flight.
     */
    @Column(nullable = false)
    private Double basePrice;

    /**
     * The seats associated with the flight.
     */
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<FlightSeat> flightSeats;
}
