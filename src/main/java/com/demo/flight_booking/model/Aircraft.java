package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(nullable = false, length = 50)
    private String aircraftModel;
    private Integer aircraftTotalCapacity;
    private Integer aircraftEconomySeats;
    private Integer aircraftPremiumSeats;
    private Integer aircraftBusinessSeats;
    private Integer aircraftFirstClassSeats;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<SeatClass> seatClasses;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<Flight> flights;
}
