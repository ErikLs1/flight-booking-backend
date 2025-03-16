package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Seat entities.
 *
 * <p>
 *     Provides a custom method to retrieve seats by a specific aircraft id.
 * </p>
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Finds all seats belong to a given aircraft.
     *
     * @param aircraftId the id of the aircraft.
     * @return a list of Seat entities associated with the specific aircraft.
     */
    List<Seat> findByAircraft_AircraftId(Long aircraftId);
}
