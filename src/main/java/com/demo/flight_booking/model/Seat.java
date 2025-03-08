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

    @Column(nullable = false)
    private Integer rowNumber;

    @Column(nullable = false, length = 10)
    private String seatLetter;

    @Column(nullable = false)
    private Boolean extraLegRoom;

    @Column(nullable = false)
    private Boolean nearExit;

    @Column(name = "window_seat", nullable = false)
    private Boolean window;

    @Column(nullable = false)
    private Boolean aisle;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<FlightSeat> flightSeats;
}
