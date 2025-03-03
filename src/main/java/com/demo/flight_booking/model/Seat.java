package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "seat_class_id", nullable = false)
    private SeatClass seatClass;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<FlightSeat> flightSeats;
}
