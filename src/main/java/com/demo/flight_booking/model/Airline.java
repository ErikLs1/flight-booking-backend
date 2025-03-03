package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "AIRLINE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineId;

    @Column(nullable = false, length = 100)
    private String airlineName;

    @Column(nullable = false, length = 10, unique = true)
    private String airlineIATACode;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private List<Flight> flights;
}
