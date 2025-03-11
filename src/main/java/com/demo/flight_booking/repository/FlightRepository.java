package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.model.enums.SeatClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

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

    // Returns aircraft model for a specific flight
    @Query("SELECT f.aircraft.aircraftModel FROM Flight f WHERE f.flightId = :flightId")
    String findAircraftModelByFlightId(Long flightId);

    // Return unique seat classes for the flight (Economy, Premium)
    @Query("SELECT DISTINCT fs.seat.seatClass.seatClassName FROM FlightSeat fs WHERE fs.flight.flightId = :flightId")
    List<SeatClassType> findSeatClassesByFlightId(Long flightId);

    // Return list of arrival and departure city for a specific flight
    @Query("SELECT f.departureAirport.airportCity, f.arrivalAirport.airportCity FROM Flight f WHERE f.flightId = :flightId")
    List<String> findFlightCitiesByFlightId(Long flightId);
}
