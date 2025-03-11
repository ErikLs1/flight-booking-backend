package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.model.enums.SeatClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
    // Find all seats for a flight that are not booked
    List<FlightSeat> findByFlight_FlightIdAndIsBookedFalse(Long flightId);

    List<FlightSeat> findByFlight_FlightId(Long flightId);

    @Query("""
        SELECT fs
        FROM FlightSeat fs
        WHERE fs.flight.flightId = :flightId
        ORDER BY fs.seat.rowNumber, fs.seat.seatLetter
    """)
    List<FlightSeat> findFlightSeatsAndSortedByRowLetter(Long flightId);

    
}
