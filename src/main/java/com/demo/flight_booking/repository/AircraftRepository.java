package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Optional<Aircraft> findByAircraftModel(String model);
}
