package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private boolean isBooked;

    @OneToMany(mappedBy = "flightSeat", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
