package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the seats assigned for the specific flight.
 *
 * <p>
 *     Stores information about whether the seat for the flight is booked or not.
 *     It is linked Seat, Flight, and Ticket.
 * </p>
 */
@Entity
@Table(
        name = "FLIGHT_SEAT",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seat_id", "flight_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightSeatId;

    /**
     * The seat associated with the flight.
     */
    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    /**
     * The flight to which this seat is assigned to.
     */
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    /**
     * Indicated whether the seat is booked.
     */
    @Column(nullable = false)
    private Boolean isBooked;

    /**
     * The tickets that were bought for the flight.
     */
    @OneToMany(mappedBy = "flightSeat", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
