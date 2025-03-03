package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
}
