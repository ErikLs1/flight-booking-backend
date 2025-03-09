package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Filters flights based on multiple optional criteria.
     *
     * @param departureAirportId the id of the departure airport.
     * @param arrivalAirportId the id of the arrival airport.
     * @param airlineId the id of the airline.
     * @param maxPrice the maximum price for the flight.
     * @param departureStartTime the start of the departure time range.
     * @param departureEndTime the end of the departure time range.
     * @return A list of Flight objects matching the given filters.
     */
    @Query("""
    SELECT f
    FROM Flight f
    WHERE
        (:depCity IS NULL OR f.departureAirport.airportCity = :depCity)
    AND
        (:arrCity IS NULL OR f.arrivalAirport.airportCity = :arrCity)
    AND
        (:airlineName IS NULL OR f.airline.airlineName = :airlineName)
    AND
        (:maxPrice IS NULL OR f.basePrice <= :maxPrice)
    AND
        (f.departureTime >= :depStart)
    AND
        (f.departureTime <= :depEnd)
    """)
    List<Flight> filterFlights(
        @Param("depCity") String departureCity,
        @Param("arrCity") String arrivalCity,
        @Param("airlineName") String airlineName,
        @Param("maxPrice") Double maxPrice,
        @Param("depStart") LocalDateTime departureStartTime,
        @Param("depEnd") LocalDateTime departureEndTime
    );
}
