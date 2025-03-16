package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface performing CRUD operations on Aircraft entities.
 *
 * <p>
 *     provides a custom method to retrieve an Aircraft by its model name.
 * </p>
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    /**
     * Finds an Aircraft by its model name.
     *
     * @param model the aircraft model name
     * @return Aircraft if found or empty if none is found.
     */
    Optional<Aircraft> findByAircraftModel(String model);
}
