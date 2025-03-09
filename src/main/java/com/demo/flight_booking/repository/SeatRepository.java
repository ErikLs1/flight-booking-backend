package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByAircraft_AircraftId(Long aircraftId);
}
