package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the aircraft used for the flight.
 *
 * <p>
 *     Stores information about aircraft model, its total seat capacity, number
 *     of seats for different seat classes on the aircraft. It is linked to SeatClas,
 *     Seat, and Flight entities.
 * </p>
 */
@Entity
@Table(name = "AIRCRAFT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;

    /**
     * The model of the aircraft. (e.g., Airbus A320).
     */
    @Column(nullable = false, length = 50)
    private String aircraftModel;

    /**
     * The total number of seats on the aircraft.
     */
    private Integer aircraftTotalCapacity;

    /**
     * Number of economy seats.
     */
    private Integer aircraftEconomySeats;

    /**
     * Number of premium seats.
     */
    private Integer aircraftPremiumSeats;

    /**
     * Number of business seats.
     */
    private Integer aircraftBusinessSeats;

    /**
     * Number of First Class seats.
     */
    private Integer aircraftFirstClassSeats;

    /**
     * The seat classes available on this aircraft.
     */
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<SeatClass> seatClasses;

    /**
     * The seats installed on this aircraft.
     */
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<Seat> seats;

    /**
     * The flights that are done by this aircraft.
     */
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<Flight> flights;
}
