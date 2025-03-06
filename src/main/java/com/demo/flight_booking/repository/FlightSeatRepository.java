package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
    // Find all seats for a flight that are booked or not booked
    List<FlightSeat> findByFlight_FlightIdAndIsBooked(Long flightId, boolean isBooked);
}
