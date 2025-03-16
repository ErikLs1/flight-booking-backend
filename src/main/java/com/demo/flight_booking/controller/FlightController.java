package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing flights.
 *
 * <p>
 *     Controller implements basic CRUD endpoints. It also provides additional endpoints for
 *     filtering flights, retrieving aircraft models, seat classes, flight cities, and seat class fee details.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/flight")
public class FlightController implements BasicController<FlightDTO, Long> {
    private final FlightService flightService;

    @Override
    public ResponseEntity<FlightDTO> create(FlightDTO dto) {
        FlightDTO saved = flightService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FlightDTO> update(Long id, FlightDTO dto) {
        FlightDTO updated = flightService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<FlightDTO> getById(Long id) {
        FlightDTO flightDTO = flightService.getById(id);
        return ResponseEntity.ok(flightDTO);
    }

    @Override
    public ResponseEntity<List<FlightDTO>> getAll() {
        List<FlightDTO> flightDTOS = flightService.getAll();
        return ResponseEntity.ok(flightDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        flightService.delete(id);
        return ResponseEntity.ok("Flight deleted!");
    }


    /**
     * Filter flights based on criteria provided in the FlightFilterDTO.
     *
     * @param filter the flight filter criteria.
     * @return a list of FlightDTO objects matching the filter.
     */
    @PostMapping("/filter")
    public ResponseEntity<List<FlightDTO>> filterFlights(@RequestBody FlightFilterDTO filter) {
        List<FlightDTO> result = flightService.searchFlights(filter);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves the aircraft model for a given flight.
     *
     * @param flightId the id of the flight.
     * @return the aircraft model as a String.
     */
    @GetMapping("/{flightId}/aircraft-model")
    public ResponseEntity<String> getAircraftModel(@PathVariable Long flightId) {
        String model = flightService.getAircraftModelByFlightId(flightId);
        return ResponseEntity.ok(model);
    }

    /**
     * Retrieves distinct seat classes available on a given flight.
     *
     * @param flightId the flight id.
     * @return a list of SeatClassType values.
     */
    @GetMapping("/{flightId}/seat-classes")
    public ResponseEntity<List<SeatClassType>> getSeatClassesByFlightId(@PathVariable Long flightId) {
        List<SeatClassType> seatClassTypes = flightService.getSeatClassesByFlightId(flightId);
        return ResponseEntity.ok(seatClassTypes);
    }

    /**
     * Retrieves the departure and arrival cities for a given flight.
     *
     * @param flightId the flight id.
     * @return a list containing departure and arrival cities.
     */
    @GetMapping("/{flightId}/cities")
    public ResponseEntity<List<String>> getFlightCitiesByFlightId(@PathVariable Long flightId) {
        List<String> cities = flightService.getFlightCitiesByFlightId(flightId);
        return ResponseEntity.ok(cities);
    }

    /**
     * Retrieve seat class fee information for a given flight.
     *
     * @param flightId the flight id.
     * @return a list of SeatClassFeeDto objects.
     */
    @GetMapping("/{flightId}/seat-class-fee")
    public ResponseEntity<List<SeatClassFeeDto>> getSeatByFlightId(@PathVariable Long flightId) {
        List<SeatClassFeeDto> seatClassFees = flightService.getSeatClassesForFlight(flightId);
        return ResponseEntity.ok(seatClassFees);
    }
}
