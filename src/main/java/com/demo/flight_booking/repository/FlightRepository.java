package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.model.enums.SeatClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface that provides methods to query flight related information.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Filters flights using provided preferences, including departure and arrival
     * cities, airline name, maximum price and departure time range.
     *
     * @param departureCity The departure city name (nullable).
     * @param arrivalCity The arrival city name (nullable).
     * @param airlineName The maximum ticket price (nullable).
     * @param maxPrice The maximum ticket price (nullable).
     * @param departureStartTime The start of the departure time range (nullable).
     * @param departureEndTime The end of the departure time range (nullable).
     * @return A list of flights matching the given preferences
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

    /**
     * Retrieves the aircraft model assigned to a specific flight.
     *
     * @param flightId The id of the flight.
     * @return The aircraft model as a String.
     */
    @Query("SELECT f.aircraft.aircraftModel FROM Flight f WHERE f.flightId = :flightId")
    String findAircraftModelByFlightId(Long flightId);

    /**
     * Retrieves the distinct seat classes available for a specific flight.
     *
     * @param flightId The id of the flight.
     * @return A list of seat class types (e.g., Economy, Business, Premium, First Class)
     */
    @Query("SELECT DISTINCT fs.seat.seatClass.seatClassName FROM FlightSeat fs WHERE fs.flight.flightId = :flightId")
    List<SeatClassType> findSeatClassesByFlightId(Long flightId);

    /**
     * Retrieves the departure and arrival cities for a specific flight.
     *
     * @param flightId The id of the flight.
     * @return A list containing two elements: departure city and arrival city.
     */
    @Query("SELECT f.departureAirport.airportCity, f.arrivalAirport.airportCity FROM Flight f WHERE f.flightId = :flightId")
    List<String> findFlightCitiesByFlightId(Long flightId);
}
