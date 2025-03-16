package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface to perform basic CRUD operations on Airport entities.
 *
 * <p>
 *     Provides a custom method to retrieve an Airport by its airport code.
 * </p>
 */
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    /**
     * Finds an Airport by its airport code.
     *
     * @param code the unique code of the airport.
     * @return An Airport or empty if none is found.
     */
    Optional<Airport> findByAirportCode(String code);
}
