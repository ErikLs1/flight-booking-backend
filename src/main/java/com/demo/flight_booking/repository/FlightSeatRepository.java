package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.model.enums.SeatClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
    // Find all seats for a flight that are not booked
    List<FlightSeat> findByFlight_FlightIdAndIsBookedFalse(Long flightId);

    // Find all seat for a flight that are not booked but for a specific class
    List<FlightSeat> findByFlight_FlightIdAndIsBookedFalseAndSeat_SeatClass_SeatClassName(Long flightId, SeatClassType seatClassType);
}
