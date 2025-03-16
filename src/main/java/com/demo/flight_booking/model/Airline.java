package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents and airline.
 *
 * <p>
 *     Stores information about an airline name and unique IATA code.
 *     It is linked to the Flight entity, representing all flights operated by the airline.
 * </p>
 */
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

    /**
     * The name of the airline.
     */
    @Column(nullable = false, length = 100)
    private String airlineName;

    /**
     * The unique IATA code for the airline.
     */
    @Column(nullable = false, length = 10, unique = true)
    private String airlineIATACode;

    /**
     * The list of flights operated by this airline.
     */
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private List<Flight> flights;
}
