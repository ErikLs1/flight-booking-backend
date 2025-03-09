package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByAirlineIATACode(String IATACode);
}
