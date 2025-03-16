package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface to perform CRUD operation on Airline entities.
 * <p>
 *     Provides a custom method retrieving an Airline entity by its IATA code.
 * </p>
 */
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

    /**
     * Finds an Airline by its IATA code.
     *
     * @param IATACode the unique IATA code of the airline.
     * @return An airline or empty if none is found.
     */
    Optional<Airline> findByAirlineIATACode(String IATACode);
}
