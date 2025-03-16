package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a seat on an Aircraft.
 *
 * <p>
 *     Stores information about seat number, row, letter and its location of the aircraft.
 *     It is linked with SeatClass and Aircraft entities.
 * </p>
 */
@Entity
@Table(name = "SEAT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    /**
     * The class of the seat (e.g., ECONOMY, FIRST CLASS).
     */
    @ManyToOne
    @JoinColumn(name = "seat_class_id", nullable = false)
    private SeatClass seatClass;

    /**
     * The aircraft to which this seat belongs.
     */
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    /**
     * The seat number (e.g., 10A).
     */
    @Column(nullable = false, length = 10)
    private String seatNumber;

    /**
     * The row number of the seat.
     */
    @Column(nullable = false)
    private Integer rowNumber;

    /**
     * The letter (column) of the seat. (e.g., A, B, C).
     */
    @Column(nullable = false, length = 10)
    private String seatLetter;

    /**
     * Indicates whether the seat has extra legroom.
     */
    @Column(nullable = false)
    private Boolean extraLegRoom;

    /**
     * Indicate whether the seat is next to the exit.
     */
    @Column(nullable = false)
    private Boolean nearExit;

    /**
     * Indicates whether the seat is next to the window.
     */
    @Column(name = "window_seat", nullable = false)
    private Boolean window;

    /**
     * Indicates whether the seat is an aisle seat.
     */
    @Column(nullable = false)
    private Boolean aisle;

    /**
     * The list of flight seat records that use this seat.
     */
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<FlightSeat> flightSeats;
}
