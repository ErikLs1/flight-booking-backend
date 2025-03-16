package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a ticket bought for the flight.
 *
 * <p>
 *     Stores information about ticket total price.
 *     It is linked with Person, FlightSeat and booking.
 * </p>
 */
@Entity
@Table(name = "TICKET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    /**
     * The passenger associated with the ticket.
     */
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    /**
     * The flight seat assigned to the ticket.
     */
    @ManyToOne
    @JoinColumn(name = "flight_seat_id", nullable = false)
    private FlightSeat flightSeat;

    /**
     * The booking associated with the ticket.
     */
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    /**
     * The price paid for the ticket.
     */
    @Column(nullable = false)
    private Double ticketPrice;

}
