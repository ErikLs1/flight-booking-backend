package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on FlightSeat entities.
 *
 * <p>
 *     Provides custom methods for retrieving flight seat flight is and booking
 *     status, as well as seats for the flight in a sorted order.
 * </p>
 */
@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
    /**
     * Finds all FlightSeat records for a specific flight that are not booked.
     *
     * @param flightId the id of the flight.
     * @return a list of FlightSeat entities that are not booked.
     */
    List<FlightSeat> findByFlight_FlightIdAndIsBookedFalse(Long flightId);

    /**
     * Finds all FlightSeat records for a specific flight.
     *
     * @param flightId the id of the flight.
     * @return a list of FlightSeat entities for the flight.
     */
    List<FlightSeat> findByFlight_FlightId(Long flightId);

    /**
     *  Retrieves all FlightSeat records for a specific flight, sorted row number and seat letter.
     *
     * @param flightId the id of the flight.
     * @return a list of FlightSeat entities sorted by row and letter.
     */
    @Query("""
        SELECT fs
        FROM FlightSeat fs
        WHERE fs.flight.flightId = :flightId
        ORDER BY fs.seat.rowNumber, fs.seat.seatLetter
    """)
    List<FlightSeat> findFlightSeatsAndSortedByRowLetter(Long flightId);

    
}
